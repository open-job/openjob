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
                .create(ServerActorConstant.CLUSTER_ACTOR_NAME)
                .withDispatcher(ServerActorConstant.CLUSTER_DISPATCHER);

        ActorRef clusterActorRef = actorSystem.actorOf(clusterProps, ServerActorConstant.CLUSTER_NAME);
        ClusterContext.setClusterActorRef(clusterActorRef);

        Props workerHeartbeatProps = PropsFactoryManager.getFactory()
                .get(actorSystem)
                .create("workerHeartbeatReportActor")
                .withRouter(new RoundRobinPool(2))
                .withDispatcher("akka.actor.worker-heartbeat-dispatcher");
        actorSystem.actorOf(workerHeartbeatProps, AkkaConstant.SERVER_HEARTBEAT_ACTOR_NAME);


        Props workerProps = PropsFactoryManager.getFactory()
                .get(actorSystem)
                .create("workerActor")
                .withRouter(new RoundRobinPool(2))
                .withDispatcher("akka.actor.worker-dispatcher");
        actorSystem.actorOf(workerProps, AkkaConstant.SERVER_WORKER_ACTOR_NAME);
    }
}
