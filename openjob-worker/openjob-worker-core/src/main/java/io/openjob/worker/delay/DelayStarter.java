package io.openjob.worker.delay;

import io.openjob.common.response.ServerHeartbeatSystemResponse;
import io.openjob.worker.init.WorkerConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
public class DelayStarter {
    public static final DelayStarter INSTANCE = new DelayStarter();

    /**
     * Delay task master.
     */
    private final DelayTaskMaster delayTaskMaster;

    /**
     * Delay version.
     */
    private final AtomicLong delayVersion = new AtomicLong(0);

    /**
     * Delay starter.
     */
    private DelayStarter() {
        this.delayTaskMaster = new DelayTaskMaster();
    }

    /**
     * Init
     */
    public void init() {
        if (WorkerConfig.getDelayEnable()) {
            this.delayTaskMaster.init();
        }
    }

    /**
     * Refresh
     */
    public void refresh(ServerHeartbeatSystemResponse systemResponse) {
        // Ignore version for refresh.
        if (this.delayVersion.get() >= systemResponse.getClusterDelayVersion()) {
            return;
        }

        // Refresh delay task master.
        this.delayTaskMaster.refresh();

        // Refresh delay task container pool for config.

        // Update delay version.
        this.delayVersion.set(systemResponse.getClusterDelayVersion());
        log.info("Delay refresh success!{}", systemResponse);
    }

    /**
     * Stop
     */
    public void stop() {
        if (!WorkerConfig.getDelayEnable()) {
            return;
        }

        // Stop  delay master.
        this.delayTaskMaster.stop();

        // Stop task container.
        DelayTaskContainerPool.getAllDelayTaskContainer().forEach((t, c) -> c.stop());
    }
}
