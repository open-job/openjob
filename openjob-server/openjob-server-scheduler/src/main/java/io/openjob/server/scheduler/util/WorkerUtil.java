package io.openjob.server.scheduler.util;

import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.dto.WorkerDTO;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class WorkerUtil {

    public static WorkerDTO selectWorkerByAppId(Long appId) {
        List<WorkerDTO> workers = ClusterContext.getWorkersByAppId(appId);
        if (CollectionUtils.isEmpty(workers)) {
            return null;
        }

        int index = ThreadLocalRandom.current().nextInt(workers.size());
        return workers.get(index);
    }
}
