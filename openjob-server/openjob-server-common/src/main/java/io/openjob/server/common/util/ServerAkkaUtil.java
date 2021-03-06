package io.openjob.server.common.util;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import io.openjob.common.SpringContext;
import io.openjob.common.constant.AkkaConstant;
import io.openjob.server.common.constant.ServerActorConstant;

import java.time.Duration;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ServerAkkaUtil {
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
        return SpringContext.getBean(ActorSystem.class).actorSelection(getActorPath(address, ServerActorConstant.ACTOR_CLUSTER));
    }

    /**
     * Cluster ask
     *
     * @param address address
     * @param message message
     * @return Object
     */
    public static Object clusterAsk(String address, Object message) {
        ActorSelection clusterActor = getClusterActor(address);
        return ask(clusterActor, message);
    }

    /**
     * Ask for actor selection
     *
     * @param actorSelection actorSelection
     * @param message        message
     * @return Object
     */
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
        return String.format(AKKA_PATH_FORMAT, AkkaConstant.SERVER_SYSTEM_NAME, address, name);
    }
}
