package io.openjob.worker.master;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.function.Function;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class TaskMasterPool {

    private static final Map<Long, BaseTaskMaster> TASK_MASTER_POOL = Maps.newConcurrentMap();

    public static BaseTaskMaster get(Long jobInstanceId) {
        return TASK_MASTER_POOL.get(jobInstanceId);
    }

    public static BaseTaskMaster get(Long jobInstanceId, Function<Long, BaseTaskMaster> creator) {
        return TASK_MASTER_POOL.computeIfAbsent(jobInstanceId, creator);
    }

    public static void remove(Long jobInstanceId) {
        TASK_MASTER_POOL.remove(jobInstanceId);
    }
}
