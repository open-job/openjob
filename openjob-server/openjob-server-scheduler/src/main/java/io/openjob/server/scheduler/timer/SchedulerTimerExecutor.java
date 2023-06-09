package io.openjob.server.scheduler.timer;

import io.openjob.server.scheduler.autoconfigure.SchedulerProperties;
import io.openjob.server.scheduler.service.SchedulerTimerService;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.2
 */
@Component
public class SchedulerTimerExecutor {
    private final ThreadPoolExecutor executorService;
    private final SchedulerTimerService schedulerTimerService;

    /**
     * New executor
     *
     * @param schedulerProperties   schedulerProperties
     * @param schedulerTimerService schedulerTimerService
     */
    public SchedulerTimerExecutor(SchedulerProperties schedulerProperties, SchedulerTimerService schedulerTimerService) {
        this.schedulerTimerService = schedulerTimerService;

        // Task container thread pool
        AtomicInteger threadId = new AtomicInteger(1);
        LinkedBlockingDeque<Runnable> blockingDeque = new LinkedBlockingDeque<>(schedulerProperties.getScheduler().getExecutorBlockingSize());
        this.executorService = new ThreadPoolExecutor(
                1,
                schedulerProperties.getScheduler().getExecutorMaxPoolSize(),
                schedulerProperties.getScheduler().getExecutorKeepAliveTime(),
                TimeUnit.SECONDS,
                blockingDeque,
                r -> new Thread(r, String.format("openjob-scheduler-executor-%s", threadId.getAndIncrement())),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        this.executorService.allowCoreThreadTimeOut(true);
    }

    /**
     * Submit task
     *
     * @param task task
     */
    public void submit(SchedulerTimerTask task) {
        this.executorService.submit(new SchedulerRunnable(task, this.schedulerTimerService));
    }

    /**
     * Scheduler runnable.
     */
    public static class SchedulerRunnable implements Runnable {
        private final SchedulerTimerTask task;
        private final SchedulerTimerService schedulerTimerService;

        public SchedulerRunnable(SchedulerTimerTask task, SchedulerTimerService schedulerTimerService) {
            this.task = task;
            this.schedulerTimerService = schedulerTimerService;
        }

        @Override
        public void run() {
            this.schedulerTimerService.run(this.task);
        }
    }
}
