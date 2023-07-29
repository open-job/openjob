package io.openjob.worker;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.openjob.worker.config.OpenjobConfig;
import io.openjob.worker.constant.WorkerConstant;
import io.openjob.worker.delay.DelayManager;
import io.openjob.worker.init.WorkerActorSystem;
import io.openjob.worker.init.WorkerChecker;
import io.openjob.worker.init.WorkerConfig;
import io.openjob.worker.init.WorkerContext;
import io.openjob.worker.init.WorkerHeartbeat;
import io.openjob.worker.init.WorkerInitializer;
import io.openjob.worker.init.WorkerRegister;
import io.openjob.worker.init.WorkerShutdown;
import io.openjob.worker.master.TaskMasterManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
@Getter
public class OpenjobWorker implements InitializingBean {

    private final WorkerChecker workerChecker;
    private final WorkerConfig workerConfig;
    private final WorkerActorSystem workerActorSystem;
    private final WorkerRegister workerRegister;
    private final WorkerHeartbeat workerHeartbeat;
    private final WorkerInitializer workerInitializer;
    private final WorkerShutdown workerShutdown;
    private final WorkerContext workerContext;

    /**
     * New OpenjobWorker
     */
    public OpenjobWorker() {
        this.workerChecker = new WorkerChecker();
        this.workerConfig = new WorkerConfig();
        this.workerActorSystem = new WorkerActorSystem();
        this.workerRegister = new WorkerRegister(this);
        this.workerHeartbeat = new WorkerHeartbeat(this);
        this.workerInitializer = new WorkerInitializer();
        this.workerShutdown = new WorkerShutdown(this);
        this.workerContext = new WorkerContext();
    }

    @Override
    public void afterPropertiesSet() {
        try {
            this.init();
        } catch (Throwable throwable) {
            log.error("Openjob worker after properties initialize failed!", throwable);
        }
    }

    /**
     * Init
     *
     * @throws Exception Exception
     */
    public synchronized void init() throws Exception {
        // Retry interval
        int retryInterval = OpenjobConfig.getInteger(WorkerConstant.WORKER_HEARTBEAT_INTERVAL, WorkerConstant.DEFAULT_WORKER_HEARTBEAT_INTERVAL);

        // Initialize executor
        ScheduledThreadPoolExecutor initializeExecutor = new ScheduledThreadPoolExecutor(
                1,
                new ThreadFactoryBuilder().setNameFormat("Openjob-initialize-thread").build(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        //Initialize continuously until complete
        initializeExecutor.scheduleWithFixedDelay(() -> {
            // Initialize complete
            if (this.doInitialize()) {
                initializeExecutor.shutdown();
                log.info("Openjob worker initialize complete!");
            }
        }, 0, retryInterval, TimeUnit.SECONDS);
    }

    /**
     * Do initialize
     *
     * @return Boolean
     */
    public synchronized Boolean doInitialize() {
        try {
            // Checker
            this.workerChecker.init();

            // Initialize worker config.
            this.workerConfig.init();

            // Initialize actor system.
            this.workerActorSystem.init();

            // Register worker.
            this.workerRegister.register();

            // Initialize worker heartbeat.
            this.workerHeartbeat.init();

            // Initialize worker initializer
            this.workerInitializer.init();

            // Initialize shutdown.
            this.workerShutdown.init();

            return true;
        } catch (Throwable ex) {
            log.error("Openjob worker initialize failed!", ex);
            return false;
        }
    }
}
