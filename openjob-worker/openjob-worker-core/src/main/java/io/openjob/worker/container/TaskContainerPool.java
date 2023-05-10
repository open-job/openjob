package io.openjob.worker.container;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.function.Function;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class TaskContainerPool {
    private static final Map<Long, TaskContainer> TASK_CONTAINER_POOL = Maps.newConcurrentMap();

    public static TaskContainer get(Long containerId) {
        return TASK_CONTAINER_POOL.get(containerId);
    }

    public static TaskContainer get(Long containerId, Function<Long, TaskContainer> creator) {
        return TASK_CONTAINER_POOL.computeIfAbsent(containerId, creator);
    }

    public static void remove(Long containerId) {
        TASK_CONTAINER_POOL.remove(containerId);
    }
}
