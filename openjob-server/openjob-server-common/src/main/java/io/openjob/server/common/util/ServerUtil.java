package io.openjob.server.common.util;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import io.openjob.common.SpringContext;
import io.openjob.common.constant.AkkaConstant;
import io.openjob.server.common.constant.ServerActorConstant;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ServerUtil {
    /**
     * Cluster actor
     *
     * @param address address
     * @return actor
     */
    public static ActorSelection getServerClusterActor(String address) {
        return SpringContext.getBean(ActorSystem.class).actorSelection(getServerActorPath(address, ServerActorConstant.ACTOR_CLUSTER));
    }

    public static ActorSelection getServerHeartbeatActor(String address) {
        return SpringContext.getBean(ActorSystem.class).actorSelection(getServerActorPath(address, ServerActorConstant.ACTOR_HEARTBEAT));
    }

    public static ActorSelection getWorkerTaskMasterActor(String address) {
        return SpringContext.getBean(ActorSystem.class).actorSelection(getWorkerActorPath(address, AkkaConstant.WORKER_ACTOR_MASTER));
    }

    /**
     * @param address address
     * @param name    actor name
     * @return actor path
     */
    private static String getServerActorPath(String address, String name) {
        return String.format(AkkaConstant.AKKA_PATH_FORMAT, AkkaConstant.SERVER_SYSTEM_NAME, address, name);
    }

    /**
     * @param address address
     * @param name    actor name
     * @return actor path
     */
    public static String getWorkerActorPath(String address, String name) {
        return String.format("akka://%s@%s/user/%s", AkkaConstant.WORKER_SYSTEM_NAME, address, name);
    }
}
