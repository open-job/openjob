package io.openjob.worker.delay;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.openjob.common.util.DateUtil;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class DelayTaskManager {
    public static final DelayTaskManager INSTANCE = new DelayTaskManager();

    /**
     * Worker heartbeat
     */
    private ScheduledExecutorService scheduledService;

    private final Map<String, Future<?>> taskId2Future = Maps.newConcurrentMap();
    private final Map<String, Long> taskId2Timeout = Maps.newConcurrentMap();


    private DelayTaskManager() {

    }

    /**
     * Init
     */
    public void init() {
        this.scheduledService = new ScheduledThreadPoolExecutor(
                1,
                new ThreadFactoryBuilder().setNameFormat("Openjob-delay-task-manager").build(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        this.scheduledService.scheduleWithFixedDelay(new TaskExecuteTimeoutRunnable(this), 1, 1, TimeUnit.SECONDS);
    }

    /**
     * Add task
     *
     * @param taskId    taskId
     * @param future    future
     * @param atTimeout atTimeout
     */
    public void addTask(String taskId, Future<?> future, Long atTimeout) {
        this.taskId2Timeout.put(taskId, atTimeout);
        this.taskId2Future.put(taskId, future);
    }

    /**
     * Remove task
     *
     * @param taskId taskId
     */
    public void remove(String taskId) {
        this.taskId2Timeout.remove(taskId);
        this.taskId2Future.remove(taskId);
    }

    /**
     * Stop and remove
     *
     * @param taskId taskId
     */
    public void stopAndRemoveTaskInstance(String taskId) {
        // Stop task
        Optional.ofNullable(this.taskId2Future.get(taskId))
                .ifPresent(f -> f.cancel(true));

        // Remove
        this.remove(taskId);
    }

    /**
     * Stop task manager
     */
    public void stop() {
        this.scheduledService.shutdown();
    }

    /**
     * Task execute runnable
     */
    private static class TaskExecuteTimeoutRunnable implements Runnable {
        private final DelayTaskManager delayTaskManager;

        private TaskExecuteTimeoutRunnable(DelayTaskManager delayTaskManager) {
            this.delayTaskManager = delayTaskManager;
        }

        @Override
        public void run() {
            Long timestamp = DateUtil.timestamp();
            this.delayTaskManager.taskId2Timeout.forEach((tid, time) -> {
                if (time > timestamp) {
                    return;
                }

                // Stop and remove
                this.delayTaskManager.stopAndRemoveTaskInstance(tid);
            });
        }
    }
}
