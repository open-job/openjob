package io.openjob.worker.init;

import com.google.common.collect.Sets;
import io.openjob.common.util.IpUtil;
import io.openjob.worker.config.OpenjobConfig;
import io.openjob.worker.constant.WorkerConstant;
import lombok.Getter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Getter
public class WorkerConfig {

    /**
     * Worker host name.
     */
    private static String workerHostName;

    /**
     * Worker port.
     */
    private static Integer workerPort;

    /**
     * Worker address.
     */
    private static String workerAddress;

    /**
     * Register app name.
     */
    private static String appName;

    /**
     * Delay enable.
     */
    private static Boolean delayEnable;

    /**
     * Server host.
     */
    private static String serverHost;

    /**
     * Init
     */
    public void init() {
        // App name.
        appName = OpenjobConfig.getString(WorkerConstant.WORKER_APP_NAME);
        if (Objects.isNull(appName)) {
            throw new RuntimeException(String.format("%s can not be null!", WorkerConstant.WORKER_APP_NAME));
        }

        workerHostName = OpenjobConfig.getString(WorkerConstant.WORKER_HOSTNAME, IpUtil.getLocalAddress());
        workerPort = OpenjobConfig.getInteger(WorkerConstant.WORKER_PORT, WorkerConstant.DEFAULT_WORKER_PORT);
        workerAddress = String.format("%s:%d", workerHostName, workerPort);
        delayEnable = OpenjobConfig.getBoolean(WorkerConstant.WORKER_DELAY_ENABLE, false);
        serverHost = OpenjobConfig.getString(WorkerConstant.SERVER_HOST, IpUtil.getLocalAddress());
    }

    public static String getWorkerHostName() {
        return workerHostName;
    }

    public static Integer getWorkerPort() {
        return workerPort;
    }

    public static String getWorkerAddress() {
        return workerAddress;
    }

    public static String getAppName() {
        return appName;
    }

    public static Boolean getDelayEnable() {
        return delayEnable;
    }

    public static String getServerHost() {
        return serverHost;
    }
}
