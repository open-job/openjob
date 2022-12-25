package io.openjob.worker.config;

import io.openjob.common.util.IpUtil;
import io.openjob.worker.constant.WorkerConstant;
import lombok.Getter;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Getter
public class OpenjobWorkerConfig {
    /**
     * Worker host name.
     */
    private final String workerHostName;

    /**
     * Worker port.
     */
    private final Integer workerPort;

    /**
     * Worker address.
     */
    private final String workerAddress;

    /**
     * Register app name.
     */
    private final String appName;

    /**
     * Delay enable.
     */
    private final Boolean delayEnable;

    /**
     * Server host.
     */
    private final String serverHost;

    /**
     * New openjob worker config.
     */
    public OpenjobWorkerConfig() {
        // Config params.
        appName = OpenjobConfig.getString(WorkerConstant.WORKER_APP_NAME);
        workerHostName = OpenjobConfig.getString(WorkerConstant.WORKER_HOSTNAME, IpUtil.getLocalAddress());
        workerPort = OpenjobConfig.getInteger(WorkerConstant.WORKER_PORT, WorkerConstant.DEFAULT_WORKER_PORT);
        workerAddress = String.format("%s:%d", workerHostName, workerPort);
        delayEnable = OpenjobConfig.getBoolean(WorkerConstant.WORKER_DELAY_ENABLE, false);
        serverHost = OpenjobConfig.getString(WorkerConstant.SERVER_HOST, IpUtil.getLocalAddress());
    }
}
