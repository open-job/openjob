package io.openjob.worker.constant;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class WorkerConstant {

    // Server config key.
    public static final String SERVER_ADDRESS = "server.address";

    // Worker config key.
    public static final String WORKER_HOSTNAME = "worker.hostname";
    public static final String WORKER_PORT = "worker.port";
    public static final String WORKER_APPID = "worker.appid";
    public static final String WORKER_AKKA_CONFIG_FILE = "worker.akka.file";
    public static final String DEFAULT_WORKER_AKKA_CONFIG_FILENAME = "akka-worker.conf";
    public static final Integer DEFAULT_WORKER_PORT = 27777;
}
