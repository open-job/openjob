package io.openjob.worker.constant;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class WorkerConstant {

    /**
     * Server config key.
     */
    public static final String SERVER_HOST = "server.host";
    public static final String SERVER_PORT = "server.port";

    /**
     * Worker config key.
     */
    public static final String WORKER_HOSTNAME = "worker.hostname";
    public static final String WORKER_PORT = "worker.port";
    public static final String WORKER_APP_NAME = "worker.app.name";
    public static final String WORKER_DELAY_ENABLE = "worker.delay.enable";
    public static final String WORKER_AKKA_CONFIG_FILE = "worker.akka.file";
    public static final String WORKER_HEARTBEAT_ACTOR_NUM = "worker.heartbeat.actor.num";
    public static final String WORKER_TASK_MASTER_ACTOR_NUM = "worker.task.master.actor.num";
    public static final String WORKER_TASK_CONTAINER_ACTOR_NUM = "worker.task.container.actor.num";
    public static final String WORKER_TASK_PERSISTENT_ACTOR_NUM = "worker.persistent.actor.num";
    public static final String WORKER_HEARTBEAT_INTERVAL = "worker.heartbeat.interval";

    public static final String WORKER_DELAY_MASTER_ACTOR_NUM = "worker.delay.master.actor.num";
    public static final String WORKER_DELAY_PULL_SIZE = "worker.delay.pull.size";
    public static final String WORKER_DELAY_PULL_SLEEP = "worker.delay.pull.sleep";
    public static final String WORKER_DELAY_PULL_STEP = "worker.delay.pull.step";

    /**
     * Default worker config.
     */
    public static final String DEFAULT_WORKER_AKKA_CONFIG_FILENAME = "akka-worker.conf";
    public static final Integer DEFAULT_SERVER_PORT = 25520;
    public static final Integer DEFAULT_WORKER_PORT = 25531;
    public static final Integer DEFAULT_WORKER_HEARTBEAT_ACTOR_NUM = 1;
    public static final Integer DEFAULT_WORKER_TASK_MASTER_ACTOR_NUM = 32;
    public static final Integer DEFAULT_WORKER_TASK_CONTAINER_ACTOR_NUM = 32;
    public static final Integer DEFAULT_WORKER_PERSISTENT_ACTOR_NUM = 2;
    public static final Integer DEFAULT_WORKER_HEARTBEAT_INTERVAL = 6;
    public static final Integer DEFAULT_WORKER_DELAY_MASTER_ACTOR_NUM = 1;
    public static final Integer DEFAULT_WORKER_DELAY_PULL_SIZE = 8;
    public static final Long DEFAULT_WORKER_DELAY_PULL_SLEEP = 500L;
    public static final Long DEFAULT_WORKER_DELAY_PULL_STEP = 500L;

    /**
     * Task
     */
    public static final String MAP_TASK_ROOT_NAME = "MR_TASK_ROOT";
    public static final String MAP_TASK_REDUCE_NAME = "MR_TASK_REDUCE";

    public static final String BROADCAST_NAME = "BROADCAST_TASK";
}
