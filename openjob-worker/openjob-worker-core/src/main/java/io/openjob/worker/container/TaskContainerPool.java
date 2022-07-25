package io.openjob.worker.container;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.function.Function;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public abstract class TaskContainerPool {
    private static final Map<String, TaskContainer> TASK_CONTAINER_POOL = Maps.newConcurrentMap();

    public static TaskContainer get(String containerId) {
        return TASK_CONTAINER_POOL.get(containerId);
    }

    public static TaskContainer get(String containerId, Function<String, TaskContainer> creator) {
        return TASK_CONTAINER_POOL.computeIfAbsent(containerId, creator);
    }

    public static void remove(String containerId) {
        TASK_CONTAINER_POOL.remove(containerId);
    }

    abstract public void submit(Long jobId, Long jonInstanceId, Long taskId, Integer consumerNum, TaskContainer taskContainer);

    abstract public void destroy( Long jonInstanceId);
}
