package io.openjob.worker.master;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.openjob.common.constant.JobInstanceStopEnum;
import io.openjob.common.util.DateUtil;
import io.openjob.worker.entity.Task;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Slf4j
public class TaskMasterManager {
    public static final TaskMasterManager INSTANCE = new TaskMasterManager();
    private ScheduledExecutorService scheduledService;
    private final Map<Long, Long> taskId2timeout = Maps.newConcurrentMap();

    private final AtomicBoolean isInit = new AtomicBoolean(false);

    private TaskMasterManager() {

    }

    /**
     * Init
     */
    public void init() {
        // Already initialized
        if (this.isInit.get()) {
            return;
        }

        this.scheduledService = new ScheduledThreadPoolExecutor(
                1,
                new ThreadFactoryBuilder().setNameFormat("Openjob-task-master-manager").build(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        this.scheduledService.scheduleWithFixedDelay(new TaskExecuteTimeoutRunnable(this), 1, 1, TimeUnit.SECONDS);

        // Initialized
        this.isInit.set(true);
    }

    public void addTask(Long instanceId, Long atTime) {
        this.taskId2timeout.put(instanceId, atTime);
    }

    public void remove(Long instanceId) {
        this.taskId2timeout.remove(instanceId);
    }

    public void stop() {
        this.scheduledService.shutdown();
    }

    private static class TaskExecuteTimeoutRunnable implements Runnable {
        private final TaskMasterManager taskMasterManager;

        private TaskExecuteTimeoutRunnable(TaskMasterManager taskMasterManager) {
            this.taskMasterManager = taskMasterManager;
        }

        @Override
        public void run() {
            this.taskMasterManager.taskId2timeout.forEach((i, t) -> {
                // Not timeout
                Long now = DateUtil.timestamp();
                if (t > now) {
                    return;
                }

                try {
                    TaskMaster taskMaster = Optional.ofNullable(TaskMasterPool.get(i)).orElseThrow(() -> new RuntimeException("Task master is not existed! instanceId" + i));
                    taskMaster.stop(JobInstanceStopEnum.TIMEOUT.getType());
                } catch (Throwable throwable) {
                    log.error("Job task timeout and stop failed!", throwable);
                }
            });
        }
    }
}
