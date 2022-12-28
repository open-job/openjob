package io.openjob.worker;

import io.openjob.worker.delay.DelayStarter;
import io.openjob.worker.init.WorkerActorSystem;
import io.openjob.worker.init.WorkerConfig;
import io.openjob.worker.init.WorkerHeartbeat;
import io.openjob.worker.init.WorkerRegister;
import io.openjob.worker.init.WorkerShutdown;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
public class OpenjobWorker implements InitializingBean {

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
        WorkerConfig.INSTANCE.init();

        // Initialize actor system.
        WorkerActorSystem.INSTANCE.init();

        // Register worker.
        WorkerRegister.INSTANCE.register();

        // Initialize worker heartbeat.
        WorkerHeartbeat.INSTANCE.init();

        // Initialize delay.
        DelayStarter.INSTANCE.init();

        // Initialize shutdown.
        WorkerShutdown.INSTANCE.init();
    }
}
