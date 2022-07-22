package io.openjob.server.cluster;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinPool;
import com.typesafe.config.Config;
import io.openjob.common.constant.AkkaConstant;
import io.openjob.server.cluster.service.StartService;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.actor.PropsFactoryManager;
import io.openjob.server.common.constant.ServerActorConstant;
import io.openjob.server.common.constant.AkkaConfigConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class ClusterServer {
    private final ActorSystem actorSystem;
    private final StartService serverService;

    @Autowired
    public ClusterServer(ActorSystem actorSystem, StartService serverService) {
        this.actorSystem = actorSystem;
        this.serverService = serverService;
    }

    /**
     * Cluster Start
     */
    public void start() {
        //Create actor
        this.createActor();

        // Join server
        Config config = actorSystem.settings().config();
        Integer port = config.getInt(AkkaConfigConstant.AKKA_REMOTE_PORT);
        String hostname = config.getString(AkkaConfigConstant.AKKA_REMOTE_HOSTNAME);
        serverService.start(hostname, port);

    }

    /**
     * Create cluster actor
     */
    public void createActor() {
        Props clusterProps = PropsFactoryManager.getFactory()
                .get(actorSystem)
                .create(ServerActorConstant.BEAN_ACTOR_CLUSTER)
                .withDispatcher(ServerActorConstant.DISPATCHER_CLUSTER);

        ActorRef clusterActorRef = actorSystem.actorOf(clusterProps, ServerActorConstant.ACTOR_CLUSTER);
        ClusterContext.setClusterActorRef(clusterActorRef);

        // Worker actor.
        Props workerProps = PropsFactoryManager.getFactory()
                .get(actorSystem)
                .create(ServerActorConstant.BEAN_ACTOR_WORKER)
                .withRouter(new RoundRobinPool(2))
                .withDispatcher(ServerActorConstant.DISPATCHER_WORKER);
        actorSystem.actorOf(workerProps, AkkaConstant.SERVER_ACTOR_WORKER);

        // Worker heartbeat.
        Props workerHeartbeatProps = PropsFactoryManager.getFactory()
                .get(actorSystem)
                .create(ServerActorConstant.BEAN_ACTOR_WORKER_HEARTBEAT)
                .withRouter(new RoundRobinPool(2))
                .withDispatcher(ServerActorConstant.DISPATCHER_WORKER_HEARTBEAT);
        actorSystem.actorOf(workerHeartbeatProps, AkkaConstant.SERVER_ACTOR_WORKER_HEARTBEAT);
    }
}
