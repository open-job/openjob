package io.openjob.worker.util;

import akka.actor.ActorSelection;
import io.openjob.common.constant.AkkaConstant;
import io.openjob.worker.OpenjobWorker;
import io.openjob.worker.config.OpenjobConfig;
import io.openjob.worker.constant.WorkerConstant;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class WorkerUtil {

    public static ActorSelection getServerWorkerActor() {
        String address = getServerAddress();
        return OpenjobWorker.getActorSystem().actorSelection(getServerActorPath(address, AkkaConstant.SERVER_ACTOR_WORKER));
    }

    public static ActorSelection getServerHeartbeatActor() {
        String address = getServerAddress();
        return OpenjobWorker.getActorSystem().actorSelection(getServerActorPath(address, AkkaConstant.SERVER_ACTOR_WORKER_HEARTBEAT));
    }

    /**
     * @param address address
     * @param name    actor name
     * @return actor path
     */
    public static String getServerActorPath(String address, String name) {
        return String.format(AkkaConstant.AKKA_PATH_FORMAT, AkkaConstant.SERVER_SYSTEM_NAME, address, name);
    }

    /**
     * @param address address
     * @param name    actor name
     * @return actor path
     */
    public static String getWorkerActorPath(String address, String name) {
        return String.format(AkkaConstant.AKKA_PATH_FORMAT, AkkaConstant.WORKER_SYSTEM_NAME, address, name);
    }

    public static String getServerAddress() {
        return String.format("%s:%d", OpenjobConfig.getString(WorkerConstant.SERVER_HOST), OpenjobConfig.getInteger(WorkerConstant.SERVER_PORT));
    }
}
