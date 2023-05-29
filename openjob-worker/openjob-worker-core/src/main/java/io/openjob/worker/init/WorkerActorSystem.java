package io.openjob.worker.init;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinPool;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.openjob.common.constant.AkkaConstant;
import io.openjob.worker.actor.DelayTaskMasterActor;
import io.openjob.worker.actor.TaskContainerActor;
import io.openjob.worker.actor.TaskMasterActor;
import io.openjob.worker.actor.WorkerHeartbeatActor;
import io.openjob.worker.actor.WorkerPersistentRoutingActor;
import io.openjob.worker.config.OpenjobConfig;
import io.openjob.worker.constant.WorkerAkkaConstant;
import io.openjob.worker.constant.WorkerConstant;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
public class WorkerActorSystem {

    /**
     * Actor system.
     */
    private static ActorSystem actorSystem;

    /**
     * Persistent routing ref.
     */
    private static ActorRef persistentRoutingRef;

    /**
     * Initialize status
     */
    private final AtomicBoolean isInit = new AtomicBoolean(false);

    /**
     * Init
     */
    public void init() {
        // Already initialized
        if (this.isInit.get()) {
            return;
        }

        String akkaConfigFile = OpenjobConfig.getString(WorkerConstant.WORKER_AKKA_CONFIG_FILE, WorkerConstant.DEFAULT_WORKER_AKKA_CONFIG_FILENAME);
        Config defaultConfig = ConfigFactory.load(akkaConfigFile);
        Map<String, String> newConfig = new HashMap<>(16);

        // Remote
        newConfig.put("akka.remote.artery.canonical.hostname", WorkerConfig.getWorkerHost());
        newConfig.put("akka.remote.artery.canonical.port", String.valueOf(WorkerConfig.getWorkerPort()));

        // Persistence
        newConfig.put("akka.persistence.journal.leveldb.dir", this.getPersistencePath("journal"));
        newConfig.put("akka.persistence.snapshot-store.local.dir", this.getPersistencePath("snapshots"));


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
        Props persistentProps = Props.create(WorkerPersistentRoutingActor.class, persistentNum)
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

        // Delay task
        if (WorkerConfig.getDelayEnable()) {
            int delayMasterNum = OpenjobConfig.getInteger(WorkerConstant.WORKER_DELAY_MASTER_ACTOR_NUM, WorkerConstant.DEFAULT_WORKER_DELAY_MASTER_ACTOR_NUM);
            Props delayMasterProps = Props.create(DelayTaskMasterActor.class)
                    .withRouter(new RoundRobinPool(delayMasterNum))
                    .withDispatcher(WorkerAkkaConstant.DISPATCHER_DELAY_MASTER);
            actorSystem.actorOf(delayMasterProps, AkkaConstant.WORKER_ACTOR_DELAY_MASTER);
        }

        // Initialized
        this.isInit.set(true);
    }

    public static ActorSystem getActorSystem() {
        return actorSystem;
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
     * Get persistence
     *
     * @param type type
     * @return String
     */
    private String getPersistencePath(String type) {
        return String.format("target/%s.%d/%s", WorkerConfig.getWorkerHost(), WorkerConfig.getWorkerPort(), type);
    }
}
