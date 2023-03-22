package io.openjob.worker.constant;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class WorkerAkkaConstant {
    /**
     * Dispatcher
     */
    public static final String DISPATCHER_HEARTBEAT = "akka.actor.heartbeat-dispatcher";
    public static final String DISPATCHER_TASK_MASTER = "akka.actor.task-master-dispatcher";
    public static final String DISPATCHER_TASK_CONTAINER = "akka.actor.task-container-dispatcher";
    public static final String DISPATCHER_PERSISTENT_ROUTING = "akka.actor.persistent-routing-dispatcher";
    public static final String DISPATCHER_DELAY_MASTER = "akka.actor.delay-master-dispatcher";

    /**
     * Routing
     */
    public static final String ACTOR_CONTAINER = "task-container";
    public static final String ACTOR_PERSISTENT_ROUTING = "persistent-routing";

    /**
     * Path
     */
    public static final String PATH_TASK_CONTAINER = "/user/" + ACTOR_CONTAINER;
}
