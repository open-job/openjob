package io.openjob.server.common.util;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import io.openjob.server.common.SpringContext;
import io.openjob.server.common.constant.ActorConstant;

import java.time.Duration;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

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
    public static ActorSelection getClusterActor(String address) {
        return SpringContext.getBean(ActorSystem.class).actorSelection(getActorPath(address, ActorConstant.CLUSTER_NAME));
    }

    /**
     * Server actor
     *
     * @param address address
     * @return actor
     */
    public static ActorSelection getServerActor(String address) {
        return SpringContext.getBean(ActorSystem.class).actorSelection(getActorPath(address, ActorConstant.SERVER_NAME));
    }

    /**
     * Worker actor
     *
     * @param address address
     * @return actor
     */
    public static ActorSelection getWorkerActor(String address) {
        return SpringContext.getBean(ActorSystem.class).actorSelection(getActorPath(address, ActorConstant.WORKER_NAME));
    }

    public static Object clusterAsk(String address, Object message) {
        ActorSelection clusterActor = getClusterActor(address);
        return ask(clusterActor, message);
    }

    public static Object ask(ActorSelection actorSelection, Object message) {
        try {
            CompletionStage<Object> ask = Patterns.ask(actorSelection, message, Duration.ofMillis(200));
            return ask.toCompletableFuture().get(200, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * @param address address
     * @param name    actor name
     * @return actor path
     */
    private static String getActorPath(String address, String name) {
        return String.format(AKKA_PATH_FORMAT, ActorConstant.SYSTEM_NAME, address, name);
    }
}
