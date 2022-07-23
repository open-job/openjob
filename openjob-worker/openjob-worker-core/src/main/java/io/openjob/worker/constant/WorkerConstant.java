package io.openjob.worker.constant;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class WorkerConstant {

    // Server config key.
    public static final String SERVER_HOST = "server.host";
    public static final String SERVER_PORT = "server.port";

    // Routing
    public static final String ROUTING_HEARTBEAT = "heartbeat";

    // Worker config key.
    public static final String WORKER_HOSTNAME = "worker.hostname";
    public static final String WORKER_PORT = "worker.port";
    public static final String WORKER_APPID = "worker.appid";
    public static final String WORKER_AKKA_CONFIG_FILE = "worker.akka.file";
    public static final String WORKER_HEARTBEAT_ACTOR_NUM = "worker.heartbeat.actor.num";
    public static final String WORKER_JOB_INSTANCE_ACTOR_NUM = "worker.job.instance.actor.num";
    public static final String WORKER_CONTAINER_ACTOR_NUM = "worker.container.actor.num";
    public static final String WORKER_TASK_ACTOR_NUM = "worker.task.actor.num";
    public static final String WORKER_PERSISTENCE_ACTOR_NUM = "worker.persistence.actor.num";
    public static final String WORKER_HEARTBEAT_INTERVAL = "worker.heartbeat.interval";

    // Default worker config.
    public static final String DEFAULT_WORKER_AKKA_CONFIG_FILENAME = "akka-worker.conf";
    public static final Integer DEFAULT_WORKER_PORT = 27777;
    public static final Integer DEFAULT_WORKER_HEARTBEAT_ACTOR_NUM = 1;
    public static final Integer DEFAULT_WORKER_TASK_ACTOR_NUM = 128;
    public static final Integer DEFAULT_WORKER_JOB_INSTANCE_TASK_ACTOR_NUM = 128;
    public static final Integer DEFAULT_WORKER_CONTAINER_TASK_ACTOR_NUM = 128;
    public static final Integer DEFAULT_WORKER_PERSISTENCE_ACTOR_NUM = 128;
    public static final Integer DEFAULT_WORKER_HEARTBEAT_INTERVAL = 6;
}
