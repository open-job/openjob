package io.openjob.worker.master;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.openjob.worker.dao.TaskDAO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
public class TaskMasterPool {

    private static final Map<Long, TaskMaster> TASK_MASTER_POOL = Maps.newConcurrentMap();

    public static Boolean contains(Long jobInstanceId) {
        return TASK_MASTER_POOL.containsKey(jobInstanceId);
    }

    public static TaskMaster get(Long jobInstanceId) {
        return TASK_MASTER_POOL.get(jobInstanceId);
    }

    public static TaskMaster get(Long jobInstanceId, Function<Long, TaskMaster> creator) {
        return TASK_MASTER_POOL.computeIfAbsent(jobInstanceId, creator);
    }

    public static void remove(Long jobInstanceId) {
        TASK_MASTER_POOL.remove(jobInstanceId);
    }

    /**
     * Get running task.
     *
     * @return List
     */
    public static List<Long> getRunningTask() {
        List<Long> jobInstanceIds = Lists.newArrayList();
        TASK_MASTER_POOL.forEach((i, t) -> {
            if (t.getRunning()) {
                jobInstanceIds.add(i);
            }
        });

        return jobInstanceIds;
    }

    /**
     * Offline workers.
     *
     * @param offlineWorkers offline workers.
     */
    public static void offlineWorkers(Set<String> offlineWorkers) {
        // Set all task of offline worker to exception status.
        Integer size = TaskDAO.INSTANCE.batchUpdateFailoverByWorkerAddress(new ArrayList<>(offlineWorkers));
        log.info("Update by offline workerAddress list success! count={}", size);
    }
}
