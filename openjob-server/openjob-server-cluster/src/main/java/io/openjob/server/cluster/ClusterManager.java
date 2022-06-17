package io.openjob.server.cluster;

import akka.actor.ActorSystem;
import akka.actor.Props;
import io.openjob.server.common.actor.PropsFactoryManager;
import io.openjob.server.common.constant.ActorConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class ClusterManager {
    private final ActorSystem actorSystem;

    @Autowired
    public ClusterManager(ActorSystem actorSystem) {
        this.actorSystem = actorSystem;

    }

    public void init() {
        Props serverProps = PropsFactoryManager.getFactory()
                .get(actorSystem)
                .Create(ActorConstant.CLUSTER_ACTOR_NAME)
                .withDispatcher(ActorConstant.CLUSTER_DISPATCHER);

        actorSystem.actorOf(serverProps, ActorConstant.CLUSTER_NAME);

        // Register server

        // Refresh job sharding

        // Broadcast cluster servers
    }
}
