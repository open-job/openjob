package io.openjob.worker.container;

import com.google.common.collect.Maps;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import io.openjob.worker.context.JobContext;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
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

    public abstract void submit(Long jobId, Long jobInstanceId, Long taskId, Integer consumerNum, TaskContainer taskContainer);

    public abstract void destroy(Long jonInstanceId);

    public abstract void setJobContext(JobContext jobContext);

    public abstract void removeJobContext();

    public abstract JobContext getJobContext();
}
