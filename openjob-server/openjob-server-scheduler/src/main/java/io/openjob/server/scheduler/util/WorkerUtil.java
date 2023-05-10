package io.openjob.server.scheduler.util;

import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.dto.WorkerDTO;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class WorkerUtil {

    /**
     * Select one worker by appid.
     *
     * @param appId        appId
     * @param failoverList failover list.
     * @return WorkerDTO
     */
    public static WorkerDTO selectWorkerByAppId(Long appId, Set<String> failoverList) {
        List<WorkerDTO> workers = ClusterContext.getWorkersByAppId(appId);
        if (CollectionUtils.isEmpty(workers)) {
            return null;
        }

        // Remove failover workers.
        List<WorkerDTO> availableWorkers = workers.stream().filter((w) -> !failoverList.contains(w.getAddress()))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(availableWorkers)) {
            return null;
        }

        int index = ThreadLocalRandom.current().nextInt(availableWorkers.size());
        return availableWorkers.get(index);
    }
}
