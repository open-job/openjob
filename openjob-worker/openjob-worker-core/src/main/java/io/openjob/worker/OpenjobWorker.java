package io.openjob.worker;

import io.openjob.worker.delay.DelayManager;
import io.openjob.worker.init.WorkerActorSystem;
import io.openjob.worker.init.WorkerConfig;
import io.openjob.worker.init.WorkerHeartbeat;
import io.openjob.worker.init.WorkerRegister;
import io.openjob.worker.init.WorkerShutdown;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
@Getter
public class OpenjobWorker implements InitializingBean {

    private final WorkerConfig workerConfig;
    private final WorkerActorSystem workerActorSystem;
    private final WorkerRegister workerRegister;
    private final WorkerHeartbeat workerHeartbeat;
    private final DelayManager delayManager;
    private final WorkerShutdown workerShutdown;

    public OpenjobWorker() {
        this.workerConfig = new WorkerConfig();
        this.workerActorSystem = new WorkerActorSystem();
        this.workerRegister = new WorkerRegister();
        this.workerHeartbeat = new WorkerHeartbeat(this);
        this.delayManager = new DelayManager();
        this.workerShutdown = new WorkerShutdown(this);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }

    /**
     * Init
     *
     * @throws Exception Exception
     */
    public synchronized void init() throws Exception {
        // Initialize worker config.
        this.workerConfig.init();

        // Initialize actor system.
        this.workerActorSystem.init();

        // Register worker.
        this.workerRegister.register();

        // Initialize worker heartbeat.
        this.workerHeartbeat.init();

        // Initialize delay.
        this.delayManager.init();

        // Initialize shutdown.
        this.workerShutdown.init();
    }
}
