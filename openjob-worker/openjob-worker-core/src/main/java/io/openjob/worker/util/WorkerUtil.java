package io.openjob.worker.util;

import akka.actor.ActorSelection;
import io.openjob.common.constant.AkkaConstant;
import io.openjob.worker.config.OpenjobConfig;
import io.openjob.worker.constant.WorkerAkkaConstant;
import io.openjob.worker.constant.WorkerConstant;
import io.openjob.worker.exception.BatchUpdateStatusException;
import io.openjob.worker.init.WorkerActorSystem;
import io.openjob.worker.init.WorkerConfig;
import io.openjob.worker.init.WorkerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
public class WorkerUtil {

    public static String selectOneWorker() {
        List<String> workers = new ArrayList<>(WorkerContext.getOnlineWorkers());
        int index = ThreadLocalRandom.current().nextInt(workers.size());
        return workers.get(index);
    }


    public static ActorSelection getServerWorkerActor() {
        String address = getServerAddress();
        return WorkerActorSystem.getActorSystem().actorSelection(getServerActorPath(address, AkkaConstant.SERVER_ACTOR_WORKER));
    }

    public static ActorSelection getServerWorkerJobInstanceActor() {
        String address = getServerAddress();
        return WorkerActorSystem.getActorSystem().actorSelection(getServerActorPath(address, AkkaConstant.SERVER_ACTOR_WORKER_INSTANCE));
    }

    public static ActorSelection getServerWorkerJobInstanceTaskLogActor() {
        String address = getServerAddress();
        return WorkerActorSystem.getActorSystem().actorSelection(getServerActorPath(address, AkkaConstant.SERVER_ACTOR_WORKER_INSTANCE_TASK_LOG));
    }

    public static ActorSelection getServerHeartbeatActor() {
        String address = getServerAddress();
        return WorkerActorSystem.getActorSystem().actorSelection(getServerActorPath(address, AkkaConstant.SERVER_ACTOR_WORKER_HEARTBEAT));
    }

    public static ActorSelection getServerDelayInstanceActor() {
        String address = getServerAddress();
        return WorkerActorSystem.getActorSystem().actorSelection(getServerActorPath(address, AkkaConstant.SERVER_ACTOR_WORKER_DELAY_INSTANCE));
    }

    public static ActorSelection getServerDelayInstancePullActor() {
        String address = getServerAddress();
        return WorkerActorSystem.getActorSystem().actorSelection(getServerActorPath(address, AkkaConstant.SERVER_ACTOR_WORKER_DELAY_INSTANCE_PULL));
    }

    public static ActorSelection getServerDelayStatusActor() {
        String address = getServerAddress();
        return WorkerActorSystem.getActorSystem().actorSelection(getServerActorPath(address, AkkaConstant.SERVER_ACTOR_WORKER_DELAY_INSTANCE_STATUS));
    }

    /**
     * @param address address
     * @param name    actor name
     * @return actor path
     */
    public static String getServerActorPath(String address, String name) {
        return String.format(AkkaConstant.AKKA_PATH_FORMAT, AkkaConstant.SERVER_SYSTEM_NAME, address, name);
    }

    public static String getServerAddress() {
        return String.format("%s:%d", WorkerConfig.getServerHost(), OpenjobConfig.getInteger(WorkerConstant.SERVER_PORT));
    }

    public static String getWorkerMasterActorPath(String address) {
        return getWorkerActorPath(address, AkkaConstant.WORKER_PATH_TASK_MASTER);
    }

    public static String getWorkerContainerActorPath(String address) {
        return getWorkerActorPath(address, WorkerAkkaConstant.PATH_TASK_CONTAINER);
    }

    public static String getWorkerActorPath(String address, String path) {
        return String.format("akka://%s@%s%s", AkkaConstant.WORKER_SYSTEM_NAME, address, path);
    }

    /**
     * Batch update status supplier.
     *
     * @param retryTimes retryTimes
     * @param supplier   supplier
     * @return Integer
     * @throws InterruptedException exception
     */
    public static Integer batchUpdateStatusSupplier(Integer retryTimes, Supplier<Integer> supplier) throws InterruptedException {
        for (int i = 0; i < retryTimes; i++) {
            try {
                return supplier.get();
            } catch (BatchUpdateStatusException exception) {
                int times = (i + 1);
                log.warn("Batch update supplier failed! times={} {}", times, exception.getMessage());
                Thread.sleep(times * 1000L);
            }
        }
        return 0;
    }
}
