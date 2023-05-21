package io.openjob.server.cluster;

import akka.Done;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.CoordinatedShutdown;
import akka.actor.Props;
import akka.routing.RoundRobinPool;
import com.typesafe.config.Config;
import io.openjob.common.constant.AkkaConstant;
import io.openjob.server.cluster.manager.FailManager;
import io.openjob.server.cluster.service.ClusterService;
import io.openjob.server.cluster.service.JoinService;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.actor.PropsFactoryManager;
import io.openjob.server.common.constant.AkkaConfigConstant;
import io.openjob.server.common.constant.ServerActorConstant;
import io.openjob.server.scheduler.autoconfigure.SchedulerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scala.concurrent.Future;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Component
public class ClusterServer {
    private final ActorSystem actorSystem;
    private final SchedulerProperties schedulerProperties;
    private final JoinService joinService;
    private final FailManager failManager;
    private final ClusterService clusterService;

    @Autowired
    public ClusterServer(ActorSystem actorSystem, SchedulerProperties schedulerProperties, JoinService joinService, FailManager failManager, ClusterService clusterService) {
        this.actorSystem = actorSystem;
        this.schedulerProperties = schedulerProperties;
        this.joinService = joinService;

        this.failManager = failManager;
        this.clusterService = clusterService;
    }

    /**
     * Cluster Start
     */
    public void start() {
        //Create actor
        this.createActor();

        // Join server
        Config config = actorSystem.settings().config();
        Integer bindPort = config.getInt(AkkaConfigConstant.AKKA_BIND_PORT);
        String bindHostname = config.getString(AkkaConfigConstant.AKKA_BIND_HOSTNAME);
        this.joinService.join(bindHostname, bindPort);

        // Register coordinated shutdown.
        this.registerCoordinatedShutdown();

        // Start success to set running.
        this.clusterService.setRunning();
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

        // Worker heartbeat actor.
        Props workerHeartbeatProps = PropsFactoryManager.getFactory()
                .get(actorSystem)
                .create(ServerActorConstant.BEAN_ACTOR_WORKER_HEARTBEAT)
                .withRouter(new RoundRobinPool(2))
                .withDispatcher(ServerActorConstant.DISPATCHER_WORKER_HEARTBEAT);
        actorSystem.actorOf(workerHeartbeatProps, AkkaConstant.SERVER_ACTOR_WORKER_HEARTBEAT);

        // Worker job instance status actor.
        Props instanceStatusProps = PropsFactoryManager.getFactory()
                .get(actorSystem)
                .create(ServerActorConstant.BEAN_ACTOR_WORKER_INSTANCE_STATUS)
                .withRouter(new RoundRobinPool(2))
                .withDispatcher(ServerActorConstant.DISPATCHER_WORKER_INSTANCE_STATUS);
        actorSystem.actorOf(instanceStatusProps, AkkaConstant.SERVER_ACTOR_WORKER_INSTANCE);

        // Worker job instance log actor.
        Props instanceLogProps = PropsFactoryManager.getFactory()
                .get(actorSystem)
                .create(ServerActorConstant.BEAN_ACTOR_WORKER_INSTANCE_TASK_LOG)
                .withRouter(new RoundRobinPool(2))
                .withDispatcher(ServerActorConstant.DISPATCHER_WORKER_INSTANCE_LOG);
        actorSystem.actorOf(instanceLogProps, AkkaConstant.SERVER_ACTOR_WORKER_INSTANCE_TASK_LOG);

        if (this.schedulerProperties.getDelay().getEnable()) {
            this.createDelayActor();
        }
    }

    /**
     * Create delay actor.
     */
    public void createDelayActor() {
        // Worker delay instance actor.
        Props instanceProps = PropsFactoryManager.getFactory()
                .get(actorSystem)
                .create(ServerActorConstant.BEAN_ACTOR_WORKER_DELAY_INSTANCE)
                .withRouter(new RoundRobinPool(2))
                .withDispatcher(ServerActorConstant.DISPATCHER_WORKER_DELAY_INSTANCE);
        actorSystem.actorOf(instanceProps, AkkaConstant.SERVER_ACTOR_WORKER_DELAY_INSTANCE);

        // Worker delay instance pull actor.
        Props instancePullProps = PropsFactoryManager.getFactory()
                .get(actorSystem)
                .create(ServerActorConstant.BEAN_ACTOR_WORKER_DELAY_INSTANCE_PULL)
                .withRouter(new RoundRobinPool(2))
                .withDispatcher(ServerActorConstant.DISPATCHER_WORKER_DELAY_INSTANCE_PULL);
        actorSystem.actorOf(instancePullProps, AkkaConstant.SERVER_ACTOR_WORKER_DELAY_INSTANCE_PULL);

        // Worker delay instance status actor.
        Props instanceStatusProps = PropsFactoryManager.getFactory()
                .get(actorSystem)
                .create(ServerActorConstant.BEAN_ACTOR_WORKER_DELAY_INSTANCE_STATUS)
                .withRouter(new RoundRobinPool(2))
                .withDispatcher(ServerActorConstant.DISPATCHER_WORKER_DELAY_INSTANCE_STATUS);
        actorSystem.actorOf(instanceStatusProps, AkkaConstant.SERVER_ACTOR_WORKER_DELAY_INSTANCE_STATUS);
    }

    /**
     * Register coordinated shutdown.
     */
    private void registerCoordinatedShutdown() {
        CoordinatedShutdown.get(this.actorSystem)
                .addTask(CoordinatedShutdown.PhaseServiceUnbind(),
                        "coordinated-shutdown-hook",
                        () -> {
                            this.failManager.shutdown(ClusterContext.getCurrentNode());
                            return Future.successful(Done.done());
                        });
    }
}
