package io.openjob.worker;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinPool;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.openjob.common.request.WorkerHeartbeatRequest;
import io.openjob.common.util.IpUtil;
import io.openjob.worker.actor.WorkerHeartbeatActor;
import io.openjob.worker.config.OpenjobConfig;
import io.openjob.worker.constant.WorkerConstant;
import io.openjob.worker.util.WorkerIdUtil;
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
    private static ScheduledExecutorService heartbeatService;

    private static ActorSelection serverActor;

    /**
     * Actor system.
     */
    public static ActorSystem actorSystem;

    static {
        heartbeatService = new ScheduledThreadPoolExecutor(
                1,
                new ThreadFactoryBuilder().setNameFormat("Openjob-heartbeat-thread").build(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    public synchronized void init() {
        this.checkConfig();

        String workerId = this.actorSystem();

        int heartbeatInterval = OpenjobConfig.getInteger(WorkerConstant.WORKER_HEARTBEAT_INTERVAL, WorkerConstant.DEFAULT_WORKER_HEARTBEAT_INTERVAL);
        heartbeatService.scheduleAtFixedRate(() -> {
        }, 5, heartbeatInterval, TimeUnit.SECONDS);
    }

    private String actorSystem() {
        String akkaConfigFile = OpenjobConfig.getString(WorkerConstant.WORKER_AKKA_CONFIG_FILE, WorkerConstant.DEFAULT_WORKER_AKKA_CONFIG_FILENAME);
        String hostname = OpenjobConfig.getString(WorkerConstant.WORKER_HOSTNAME, IpUtil.getLocalAddress());
        int port = OpenjobConfig.getInteger(WorkerConstant.WORKER_PORT, WorkerConstant.DEFAULT_WORKER_PORT);

        Config defaultConfig = ConfigFactory.load(akkaConfigFile);
        Map<String, String> newConfig = new HashMap<>(16);

        String workerId = WorkerIdUtil.getId(port);
        Config config = ConfigFactory.parseMap(newConfig).withFallback(defaultConfig);
        actorSystem = ActorSystem.create(workerId, config);

        log.info("Worker actor system started,address={} workerId={}", actorSystem.provider().getDefaultAddress(), workerId);

        // Heartbeat actor.
        int heartbeatNum = OpenjobConfig.getInteger(WorkerConstant.WORKER_HEARTBEAT_ACTOR_NUM, WorkerConstant.DEFAULT_WORKER_HEARTBEAT_ACTOR_NUM);
        Props props = Props.create(WorkerHeartbeatActor.class)
                .withRouter(new RoundRobinPool(heartbeatNum))
                .withDispatcher("heartbeat-dispatcher");
        actorSystem.actorOf(props, WorkerConstant.ROUTING_HEARTBEAT);

        // Job instance actor.

        // Manager actor.

        // Task actor.

        // Persistence actor.
        return workerId;
    }


    private void checkConfig() {
        String serverAddress = OpenjobConfig.getString(WorkerConstant.SERVER_ADDRESS);
        if (Objects.isNull(serverAddress)) {
            throw new RuntimeException(String.format("%s must be config", WorkerConstant.SERVER_ADDRESS));
        }

        String appid = OpenjobConfig.getString(WorkerConstant.WORKER_APPID);
        if (Objects.isNull(appid)) {
            throw new RuntimeException(String.format("%s must be config", WorkerConstant.WORKER_APPID));
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
