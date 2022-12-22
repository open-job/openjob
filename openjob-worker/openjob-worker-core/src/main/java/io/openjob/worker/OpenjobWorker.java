package io.openjob.worker;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinPool;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.openjob.common.constant.AkkaConstant;
import io.openjob.common.constant.ProtocolTypeEnum;
import io.openjob.common.request.WorkerHeartbeatRequest;
import io.openjob.common.request.WorkerStartRequest;
import io.openjob.common.request.WorkerStopRequest;
import io.openjob.common.response.Result;
import io.openjob.common.response.ServerHeartbeatResponse;
import io.openjob.common.util.FutureUtil;
import io.openjob.common.util.ResultUtil;
import io.openjob.worker.actor.TaskContainerActor;
import io.openjob.worker.actor.TaskMasterActor;
import io.openjob.worker.actor.WorkerHeartbeatActor;
import io.openjob.worker.actor.WorkerPersistentRoutingActor;
import io.openjob.worker.config.OpenjobConfig;
import io.openjob.worker.config.OpenjobWorkerConfig;
import io.openjob.worker.constant.WorkerAkkaConstant;
import io.openjob.worker.constant.WorkerConstant;
import io.openjob.worker.delay.DelayStarter;
import io.openjob.worker.util.WorkerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
public class OpenjobWorker implements InitializingBean {

    /**
     * Openjob worker config.
     */
    private static final OpenjobWorkerConfig CONFIG;

    /**
     * Worker heartbeat
     */
    private static final ScheduledExecutorService heartbeatService;

    /**
     * Actor system.
     */
    private static ActorSystem actorSystem;

    /**
     * Persistent routing ref.
     */
    private static ActorRef persistentRoutingRef;

    static {
        // Heartbeat service
        heartbeatService = new ScheduledThreadPoolExecutor(
                1,
                new ThreadFactoryBuilder().setNameFormat("Openjob-heartbeat-thread").build(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        CONFIG = new OpenjobWorkerConfig();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }

    /**
     * Init
     *
     * @throws Exception Exception
     */
    public synchronized void init() throws Exception {
        this.checkConfig();

        this.actorSystem();

        this.start();

        this.doHeartbeat();

        this.startDelay();

        // Shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    /**
     * Get actor system.
     *
     * @return ActorSystem
     */
    public static ActorSystem getActorSystem() {
        return actorSystem;
    }

    /**
     * Get openjob worker config.
     *
     * @return OpenjobWorkerConfig
     */
    public static OpenjobWorkerConfig getConfig() {
        return CONFIG;
    }

    /**
     * At least once delivery.
     *
     * @param msg    msg
     * @param sender sender
     */
    public static void atLeastOnceDelivery(Object msg, ActorRef sender) {
        persistentRoutingRef.tell(msg, sender);
    }

    /**
     * Start
     *
     * @throws Exception Exception
     */
    private void start() throws Exception {
        String serverAddress = OpenjobConfig.getString(WorkerConstant.SERVER_HOST);

        WorkerStartRequest startReq = new WorkerStartRequest();
        startReq.setAddress(CONFIG.getWorkerAddress());
        startReq.setAppName(CONFIG.getAppName());
        startReq.setProtocolType(ProtocolTypeEnum.AKKA.getType());

        try {
            Result<?> result = (Result<?>) FutureUtil.ask(WorkerUtil.getServerWorkerActor(), startReq, 3L);
            if (!ResultUtil.isSuccess(result)) {
                log.error("Register worker fail. serverAddress={} workerAddress={} message={}", serverAddress, CONFIG.getWorkerAddress(), result.getMessage());
                throw new RuntimeException(String.format("Register worker fail! message=%s", result.getMessage()));
            }

            log.info("Register worker success. serverAddress={} workerAddress={}", serverAddress, CONFIG.getWorkerAddress());
        } catch (Throwable e) {
            log.error("Register worker fail. serverAddress={} workerAddress={}", serverAddress, CONFIG.getWorkerAddress());
            throw e;
        }
    }

    /**
     * Openjob worker stop.
     *
     * @throws Exception exception
     */
    private void stop() throws Exception {
        String serverAddress = OpenjobConfig.getString(WorkerConstant.SERVER_HOST);
        String workerAddress = CONFIG.getWorkerAddress();
        String appName = CONFIG.getAppName();

        WorkerStopRequest stopRequest = new WorkerStopRequest();
        stopRequest.setAppName(appName);
        stopRequest.setAddress(workerAddress);

        Result<?> result = (Result<?>) FutureUtil.ask(WorkerUtil.getServerWorkerActor(), stopRequest, 3L);
        if (!ResultUtil.isSuccess(result)) {
            log.error("Stop worker fail. serverAddress={} workerAddress={} message={}", serverAddress, workerAddress, result.getMessage());
        }
    }

    /**
     * Shutdown
     */
    private void shutdown() {
        // Stop worker heartbeat service.
        heartbeatService.shutdownNow();

        // Stop delay.
        if (CONFIG.getDelayEnable()) {
            DelayStarter.INSTANCE.stop();
        }

        // Stop worker.
        try {
            this.stop();
        } catch (Throwable e) {
            log.error("Worker stop failed", e);
        }
    }

    /**
     * Start delay.
     */
    private void startDelay() {
        // Start delay job.
        if (CONFIG.getDelayEnable()) {
            DelayStarter.INSTANCE.init();
        }
    }

    /**
     * Refresh worker.
     */
    private void refresh(ServerHeartbeatResponse heartbeatResponse) {
        // Refresh delay.
        if (CONFIG.getDelayEnable()) {
            DelayStarter.INSTANCE.refresh(heartbeatResponse.getSystemResponse());
        }
    }

    private void doHeartbeat() {
        int heartbeatInterval = OpenjobConfig.getInteger(WorkerConstant.WORKER_HEARTBEAT_INTERVAL, WorkerConstant.DEFAULT_WORKER_HEARTBEAT_INTERVAL);
        heartbeatService.scheduleAtFixedRate(() -> {
            String workerAddress = CONFIG.getWorkerAddress();
            String serverAddress = OpenjobConfig.getString(WorkerConstant.SERVER_HOST);

            WorkerHeartbeatRequest heartbeatReq = new WorkerHeartbeatRequest();
            heartbeatReq.setAddress(workerAddress);
            heartbeatReq.setAppName(OpenjobConfig.getString(WorkerConstant.WORKER_APP_NAME));
            heartbeatReq.setVersion("1.0");
            try {
                ServerHeartbeatResponse heartbeatResponse = FutureUtil.mustAsk(WorkerUtil.getServerHeartbeatActor(), heartbeatReq, ServerHeartbeatResponse.class, 3000L);
                log.info("Worker heartbeat success. serverAddress={} workerAddress={}", serverAddress, workerAddress);

                // Refresh worker.
                this.refresh(heartbeatResponse);
            } catch (Throwable e) {
                log.error("Worker heartbeat fail. serverAddress={} workerAddress={}", serverAddress, workerAddress, e);
            }

        }, 5, heartbeatInterval, TimeUnit.SECONDS);
    }

    private void checkConfig() {
        String serverAddress = OpenjobConfig.getString(WorkerConstant.SERVER_HOST);
        if (Objects.isNull(serverAddress)) {
            throw new RuntimeException(String.format("%s must be config", WorkerConstant.SERVER_HOST));
        }

        String appName = OpenjobConfig.getString(WorkerConstant.WORKER_APP_NAME);
        if (Objects.isNull(appName)) {
            throw new RuntimeException(String.format("%s must be config", WorkerConstant.WORKER_APP_NAME));
        }
    }

    private void actorSystem() {
        String akkaConfigFile = OpenjobConfig.getString(WorkerConstant.WORKER_AKKA_CONFIG_FILE, WorkerConstant.DEFAULT_WORKER_AKKA_CONFIG_FILENAME);
        Config defaultConfig = ConfigFactory.load(akkaConfigFile);
        Map<String, String> newConfig = new HashMap<>(16);
        newConfig.put("akka.remote.artery.canonical.hostname", CONFIG.getWorkerHostName());
        newConfig.put("akka.remote.artery.canonical.port", String.valueOf(CONFIG.getWorkerPort()));


        Config config = ConfigFactory.parseMap(newConfig).withFallback(defaultConfig);
        actorSystem = ActorSystem.create(AkkaConstant.WORKER_SYSTEM_NAME, config);

        log.info("Worker actor system started,address={}", actorSystem.provider().getDefaultAddress());

        // Heartbeat actor.
        int heartbeatNum = OpenjobConfig.getInteger(WorkerConstant.WORKER_HEARTBEAT_ACTOR_NUM, WorkerConstant.DEFAULT_WORKER_HEARTBEAT_ACTOR_NUM);
        Props props = Props.create(WorkerHeartbeatActor.class)
                .withRouter(new RoundRobinPool(heartbeatNum))
                .withDispatcher(WorkerAkkaConstant.DISPATCHER_HEARTBEAT);
        actorSystem.actorOf(props, AkkaConstant.WORKER_ACTOR_HEARTBEAT);

        // At least once persistent actor.
        int persistentNum = OpenjobConfig.getInteger(WorkerConstant.WORKER_TASK_PERSISTENT_ACTOR_NUM, WorkerConstant.DEFAULT_WORKER_PERSISTENT_ACTOR_NUM);
        Props persistentProps = Props.create(WorkerPersistentRoutingActor.class, 1)
                .withDispatcher(WorkerAkkaConstant.DISPATCHER_PERSISTENT_ROUTING);
        persistentRoutingRef = actorSystem.actorOf(persistentProps, WorkerAkkaConstant.ACTOR_PERSISTENT_ROUTING);

        // Master actor.
        int taskMasterNum = OpenjobConfig.getInteger(WorkerConstant.WORKER_TASK_MASTER_ACTOR_NUM, WorkerConstant.DEFAULT_WORKER_TASK_MASTER_ACTOR_NUM);
        Props masterProps = Props.create(TaskMasterActor.class)
                .withRouter(new RoundRobinPool(taskMasterNum))
                .withDispatcher(WorkerAkkaConstant.DISPATCHER_TASK_MASTER);
        actorSystem.actorOf(masterProps, AkkaConstant.WORKER_ACTOR_MASTER);

        // Container actor.
        int taskContainerNum = OpenjobConfig.getInteger(WorkerConstant.WORKER_TASK_CONTAINER_ACTOR_NUM, WorkerConstant.DEFAULT_WORKER_TASK_CONTAINER_ACTOR_NUM);
        Props containerProps = Props.create(TaskContainerActor.class)
                .withRouter(new RoundRobinPool(taskContainerNum))
                .withDispatcher(WorkerAkkaConstant.DISPATCHER_TASK_CONTAINER);
        actorSystem.actorOf(containerProps, WorkerAkkaConstant.ACTOR_CONTAINER);
    }
}
