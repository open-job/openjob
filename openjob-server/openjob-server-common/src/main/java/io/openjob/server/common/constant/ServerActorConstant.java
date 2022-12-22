package io.openjob.server.common.constant;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ServerActorConstant {

    /**
     * Actor name.
     */
    public static final String ACTOR_CLUSTER = "cluster";
    public static final String ACTOR_HEARTBEAT = "heartbeat";

    /**
     * Actor bean name.
     */
    public static final String BEAN_ACTOR_CLUSTER = "clusterActor";
    public static final String BEAN_ACTOR_WORKER = "workerActor";
    public static final String BEAN_ACTOR_WORKER_DELAY_INSTANCE = "workerDelayInstanceActor";
    public static final String BEAN_ACTOR_WORKER_DELAY_INSTANCE_PULL = "workerDelayInstancePullActor";
    public static final String BEAN_ACTOR_WORKER_HEARTBEAT = "workerHeartbeatActor";
    public static final String BEAN_ACTOR_WORKER_INSTANCE_STATUS = "workerJobInstanceActor";
    public static final String BEAN_ACTOR_WORKER_INSTANCE_TASK_LOG = "workerJobInstanceTaskLogActor";

    /**
     * Dispatcher name.
     */
    public static final String DISPATCHER_CLUSTER = "akka.actor.cluster-dispatcher";
    public static final String DISPATCHER_WORKER = "akka.actor.worker-dispatcher";
    public static final String DISPATCHER_WORKER_HEARTBEAT = "akka.actor.worker-heartbeat-dispatcher";
    public static final String DISPATCHER_WORKER_DELAY_INSTANCE = "akka.actor.worker-delay-instance-dispatcher";
    public static final String DISPATCHER_WORKER_DELAY_INSTANCE_PULL = "akka.actor.worker-delay-instance-pull-dispatcher";
    public static final String DISPATCHER_WORKER_INSTANCE_STATUS = "akka.actor.worker-instance-status-dispatcher";
    public static final String DISPATCHER_WORKER_INSTANCE_LOG = "akka.actor.worker-instance-task-log-dispatcher";
}
