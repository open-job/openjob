package io.openjob.common.constant;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class AkkaConstant {
    // Server
    public static final String SERVER_SYSTEM_NAME = "openjob-server";
    public static final String SERVER_WORKER_ACTOR_NAME = "worker";
    public static final String SERVER_HEARTBEAT_ACTOR_NAME = "heartbeat-report";
    public static final String SERVER_MASTER_ACTOR_NAME = "master-report";

    // Worker
    public static final String WORKER_SYSTEM_NAME = "openjob-worker";
    public static final String WORKER_MASTER_ACTOR_NAME = "master";
    public static final String WORKER_MANAGER_ACTOR_NAME = "manager";
    public static final String WORKER_TASK_ACTOR_NAME = "task";
}
