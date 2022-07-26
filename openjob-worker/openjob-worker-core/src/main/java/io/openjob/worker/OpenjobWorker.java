package io.openjob.worker;

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
import io.openjob.common.response.Result;
import io.openjob.common.util.FutureUtil;
import io.openjob.common.util.IpUtil;
import io.openjob.common.util.ResultUtil;
import io.openjob.worker.actor.TaskContainerActor;
import io.openjob.worker.actor.TaskMasterActor;
import io.openjob.worker.actor.WorkerHeartbeatActor;
import io.openjob.worker.config.OpenjobConfig;
import io.openjob.worker.constant.WorkerAkkaConstant;
import io.openjob.worker.constant.WorkerConstant;
import io.openjob.worker.util.WorkerUtil;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class OpenjobWorker implements InitializingBean {

    /**
     * Worker heartbeat
     */
    private static final ScheduledExecutorService heartbeatService;

    /**
     * Actor system.
     */
    private static ActorSystem actorSystem;

    static {
        heartbeatService = new ScheduledThreadPoolExecutor(
                1,
                new ThreadFactoryBuilder().setNameFormat("Openjob-heartbeat-thread").build(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }

    public synchronized void init() throws Exception {
        this.checkConfig();

        this.actorSystem();

        this.start();

        this.doHeartbeat();

        // Shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    public void start() throws Exception {
        String workerAddress = this.getWorkerAddress();
        String serverAddress = OpenjobConfig.getString(WorkerConstant.SERVER_HOST);

        WorkerStartRequest startReq = new WorkerStartRequest();
        startReq.setAddress(workerAddress);
        startReq.setAppName(OpenjobConfig.getString(WorkerConstant.WORKER_APPID));
        startReq.setProtocolType(ProtocolTypeEnum.AKKA.getType());

        try {
            Result<?> result = (Result<?>) FutureUtil.ask(WorkerUtil.getServerWorkerActor(), startReq, 3L);
            if (!ResultUtil.isSuccess(result)) {
                log.error("Register worker fail. serverAddress={} workerAddress={} message={}", serverAddress, workerAddress, result.getMessage());
                throw new RuntimeException(String.format("Register worker fail! message=%s", result.getMessage()));
            }

            log.info("Register worker success. serverAddress={} workerAddress={}", serverAddress, workerAddress);
        } catch (Throwable e) {
            log.error("Register worker fail. serverAddress={} workerAddress={}", serverAddress, workerAddress);
            throw e;
        }
    }

    public void shutdown() {
        heartbeatService.shutdownNow();
    }

    private void doHeartbeat() {
        int heartbeatInterval = OpenjobConfig.getInteger(WorkerConstant.WORKER_HEARTBEAT_INTERVAL, WorkerConstant.DEFAULT_WORKER_HEARTBEAT_INTERVAL);
        heartbeatService.scheduleAtFixedRate(() -> {
            String workerAddress = this.getWorkerAddress();
            String serverAddress = OpenjobConfig.getString(WorkerConstant.SERVER_HOST);

            WorkerHeartbeatRequest heartbeatReq = new WorkerHeartbeatRequest();
            heartbeatReq.setAddress(workerAddress);
            heartbeatReq.setAppName(OpenjobConfig.getString(WorkerConstant.WORKER_APPID));
            heartbeatReq.setVersion("1.0");
            try {
                Result<?> result = (Result<?>) FutureUtil.ask(WorkerUtil.getServerHeartbeatActor(), heartbeatReq, 3L);
                if (!ResultUtil.isSuccess(result)) {
                    log.error("Worker heartbeat fail. serverAddress={} workerAddress={} message={}", serverAddress, workerAddress, result.getMessage());
                    throw new RuntimeException(String.format("Register worker fail! message=%s", result.getMessage()));
                }

                log.info("Worker heartbeat success. serverAddress={} workerAddress={}", serverAddress, workerAddress);
            } catch (Throwable e) {
                log.error("Register worker fail. serverAddress={} workerAddress={}", serverAddress, workerAddress);
            }

        }, 5, heartbeatInterval, TimeUnit.SECONDS);
    }

    private void checkConfig() {
        String serverAddress = OpenjobConfig.getString(WorkerConstant.SERVER_HOST);
        if (Objects.isNull(serverAddress)) {
            throw new RuntimeException(String.format("%s must be config", WorkerConstant.SERVER_HOST));
        }

        String appid = OpenjobConfig.getString(WorkerConstant.WORKER_APPID);
        if (Objects.isNull(appid)) {
            throw new RuntimeException(String.format("%s must be config", WorkerConstant.WORKER_APPID));
        }
    }

    private void actorSystem() {
        String akkaConfigFile = OpenjobConfig.getString(WorkerConstant.WORKER_AKKA_CONFIG_FILE, WorkerConstant.DEFAULT_WORKER_AKKA_CONFIG_FILENAME);
        Config defaultConfig = ConfigFactory.load(akkaConfigFile);
        Map<String, String> newConfig = new HashMap<>(16);
        newConfig.put("akka.remote.artery.canonical.hostname", this.getWorkerHostname());
        newConfig.put("akka.remote.artery.canonical.port", String.valueOf(this.getWorkerPort()));


        Config config = ConfigFactory.parseMap(newConfig).withFallback(defaultConfig);
        actorSystem = ActorSystem.create(AkkaConstant.WORKER_SYSTEM_NAME, config);

        log.info("Worker actor system started,address={}", actorSystem.provider().getDefaultAddress());

        // Heartbeat actor.
        int heartbeatNum = OpenjobConfig.getInteger(WorkerConstant.WORKER_HEARTBEAT_ACTOR_NUM, WorkerConstant.DEFAULT_WORKER_HEARTBEAT_ACTOR_NUM);
        Props props = Props.create(WorkerHeartbeatActor.class)
                .withRouter(new RoundRobinPool(heartbeatNum))
                .withDispatcher(WorkerAkkaConstant.DISPATCHER_HEARTBEAT);
        actorSystem.actorOf(props, AkkaConstant.WORKER_ACTOR_HEARTBEAT);

        // Master actor.
        Props masterProps = Props.create(TaskMasterActor.class)
                .withRouter(new RoundRobinPool(4))
                .withDispatcher(WorkerAkkaConstant.DISPATCHER_TASK_MASTER);
        actorSystem.actorOf(masterProps, AkkaConstant.WORKER_ACTOR_MASTER);

        // Container actor.
        Props containerProps = Props.create(TaskContainerActor.class)
                .withRouter(new RoundRobinPool(3))
                .withDispatcher(WorkerAkkaConstant.DISPATCHER_TASK_CONTAINER);
        actorSystem.actorOf(containerProps);
    }

    public String getWorkerAddress() {
        String hostname = this.getWorkerHostname();
        int port = this.getWorkerPort();
        return String.format("%s:%d", hostname, port);
    }

    public static ActorSystem getActorSystem() {
        return actorSystem;
    }

    private String getWorkerHostname() {
        return OpenjobConfig.getString(WorkerConstant.WORKER_HOSTNAME, IpUtil.getLocalAddress());
    }

    private Integer getWorkerPort() {
        return OpenjobConfig.getInteger(WorkerConstant.WORKER_PORT, WorkerConstant.DEFAULT_WORKER_PORT);
    }
}
