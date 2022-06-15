package io.openjob.server.core.actor;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.openjob.server.cluster.ClusterActor;
import io.openjob.server.common.constant.ActorConstant;
import io.openjob.server.common.constant.AkkaConfigConstant;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class ServerActorSystem implements InitializingBean {
    /**
     * Akka path format
     */
    public static final String AKKA_PATH_FORMAT = "akka://%s@%s/user/%s";

    /**
     * Actor system
     */
    private ActorSystem actorSystem;

    @Override
    public void afterPropertiesSet() {
        // Merge config
        Map<String, Object> newConfig = new HashMap<>();
        Config defaultConfig = ConfigFactory.load(AkkaConfigConstant.AKKA_CONFIG_NAME);
        Config mergedConfig = ConfigFactory.parseMap(newConfig).withFallback(defaultConfig);

        // Create actor system
        this.actorSystem = ActorSystem.create(ActorConstant.SYSTEM_NAME, mergedConfig);
        this.actorSystem.actorOf(Props.create(ClusterActor.class), ActorConstant.CLUSTER_NAME);
        this.actorSystem.actorOf(Props.create(ServerActor.class), ActorConstant.SERVER_NAME);
    }

    /**
     * Cluster actor
     *
     * @param address address
     * @return actor
     */
    public ActorSelection getClusterActor(String address) {
        return actorSystem.actorSelection(getActorPath(address, ActorConstant.CLUSTER_NAME));
    }

    /**
     * Server actor
     *
     * @param address address
     * @return actor
     */
    public ActorSelection getServerActor(String address) {
        return actorSystem.actorSelection(getActorPath(address, ActorConstant.SERVER_NAME));
    }

    /**
     * Worker actor
     *
     * @param address address
     * @return actor
     */
    public ActorSelection getWorkerActor(String address) {
        return actorSystem.actorSelection(getActorPath(address, ActorConstant.WORKER_NAME));
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
