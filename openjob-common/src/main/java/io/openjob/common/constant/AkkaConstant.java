package io.openjob.common.constant;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class AkkaConstant {
    // Server
    public static final String SERVER_SYSTEM_NAME = "openjob-server";
    public static final String SERVER_ACTOR_WORKER = "worker";
    public static final String SERVER_ACTOR_HEARTBEAT = "heartbeat-report";
    public static final String SERVER_ACTOR_MASTER = "master-report";

    // Worker
    public static final String WORKER_SYSTEM_NAME = "openjob-worker";
    public static final String WORKER_ACTOR_MASTER = "master";
    public static final String WORKER_ACTOR_MANAGER = "manager";
    public static final String WORKER_ACTOR_TASK = "task";
}
