package io.openjob.server.common.util;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import io.openjob.server.common.SpringContext;
import io.openjob.server.common.constant.ActorConstant;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class AkkaUtil {
    /**
     * Akka path format
     */
    public static final String AKKA_PATH_FORMAT = "akka://%s@%s/user/%s";

    /**
     * Cluster actor
     *
     * @param address address
     * @return actor
     */
    public ActorSelection getClusterActor(String address) {
        return SpringContext.getBean(ActorSystem.class).actorSelection(getActorPath(address, ActorConstant.CLUSTER_NAME));
    }

    /**
     * Server actor
     *
     * @param address address
     * @return actor
     */
    public ActorSelection getServerActor(String address) {
        return SpringContext.getBean(ActorSystem.class).actorSelection(getActorPath(address, ActorConstant.SERVER_NAME));
    }

    /**
     * Worker actor
     *
     * @param address address
     * @return actor
     */
    public ActorSelection getWorkerActor(String address) {
        return SpringContext.getBean(ActorSystem.class).actorSelection(getActorPath(address, ActorConstant.WORKER_NAME));
    }

    /**
     * @param address address
     * @param name    actor name
     * @return actor path
     */
    private String getActorPath(String address, String name) {
        return String.format(AKKA_PATH_FORMAT, address, ActorConstant.SYSTEM_NAME, name);
    }
}
