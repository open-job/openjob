package io.openjob.server.cluster;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import io.openjob.server.cluster.service.ServerService;
import io.openjob.server.common.actor.PropsFactoryManager;
import io.openjob.server.common.constant.ActorConstant;
import io.openjob.server.common.constant.AkkaConfigConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class ClusterManager {
    private final ActorSystem actorSystem;
    private final ServerService serverService;

    @Autowired
    public ClusterManager(ActorSystem actorSystem, ServerService serverService) {
        this.actorSystem = actorSystem;
        this.serverService = serverService;
    }

    public void init() {
        //Create actor
        Props serverProps = PropsFactoryManager.getFactory()
                .get(actorSystem)
                .create(ActorConstant.CLUSTER_ACTOR_NAME)
                .withDispatcher(ActorConstant.CLUSTER_DISPATCHER);

        actorSystem.actorOf(serverProps, ActorConstant.CLUSTER_NAME);

        // Join server
        Config config = actorSystem.settings().config();
        Integer port = config.getInt(AkkaConfigConstant.AKKA_REMOTE_PORT);
        String hostname = config.getString(AkkaConfigConstant.AKKA_REMOTE_HOSTNAME);
        serverService.join(hostname, port);
    }
}
