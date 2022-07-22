package io.openjob.server.common.constant;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ServerActorConstant {

    // Actor name.
    public static final String ACTOR_CLUSTER = "cluster";

    // Actor bean name.
    public static final String BEAN_ACTOR_CLUSTER = "clusterActor";
    public static final String BEAN_ACTOR_WORKER = "workerActor";
    public static final String BEAN_ACTOR_WORKER_HEARTBEAT = "workerHeartbeatActor";

    // Dispatcher name.
    public static final String DISPATCHER_CLUSTER = "akka.actor.cluster-dispatcher";
    public static final String DISPATCHER_WORKER = "akka.actor.worker-dispatcher";
    public static final String DISPATCHER_WORKER_HEARTBEAT = "akka.actor.worker-heartbeat-dispatcher";
}
