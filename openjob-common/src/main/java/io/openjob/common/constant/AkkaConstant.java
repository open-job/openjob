package io.openjob.common.constant;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class AkkaConstant {

    // Path
    public static final String AKKA_PATH_FORMAT = "akka://%s@%s/user/%s";

    // Server
    public static final String SERVER_SYSTEM_NAME = "openjob-server";
    public static final String SERVER_ACTOR_WORKER = "worker";
    public static final String SERVER_ACTOR_WORKER_HEARTBEAT = "worker-heartbeat";
    public static final String SERVER_ACTOR_WORKER_TASK_MASTER = "worker-task-master";

    // Worker
    public static final String WORKER_SYSTEM_NAME = "openjob-worker";
    public static final String WORKER_ACTOR_HEARTBEAT = "heartbeat";
    public static final String WORKER_ACTOR_MASTER = "task-master";
    public static final String WORKER_ACTOR_MANAGER = "task-manager";
    public static final String WORKER_ACTOR_TASK = "task";
}
