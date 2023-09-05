package io.openjob.worker.container;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.7
 */
@Slf4j
public class TaskContainerManager {

    public static final TaskContainerManager INSTANCE = new TaskContainerManager();
    private final Map<String, Future<?>> taskId2Future = Maps.newConcurrentMap();
    private final AtomicBoolean isInit = new AtomicBoolean(false);


    private TaskContainerManager() {

    }

    /**
     * Init
     */
    public void init() {
        // Already initialized
        if (this.isInit.get()) {
            return;
        }

        // Initialized
        this.isInit.set(true);
    }

    public void addTask(String taskId, Future<?> future) {
        this.taskId2Future.put(taskId, future);
    }

    /**
     * Remove task
     *
     * @param taskId taskId
     */
    public void removeTask(String taskId) {
        this.taskId2Future.remove(taskId);
    }

    /**
     * Stop and remove task
     *
     * @param taskId taskId
     */
    public void stopAndRemoveTask(String taskId) {
        Optional.ofNullable(this.taskId2Future.get(taskId))
                .ifPresent(f -> f.cancel(true));

        // Remove
        this.removeTask(taskId);
    }

    /**
     * Stop
     */
    public void stop() {

    }
}
