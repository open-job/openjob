package io.openjob.server.cluster.scheduling;

import io.openjob.server.cluster.constant.ClusterConstant;
import io.openjob.server.cluster.service.WorkerService;
import io.openjob.server.common.ClusterContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class WorkerScheduling {
    private final WorkerService workerService;

    @Autowired
    public WorkerScheduling(WorkerService workerService) {
        this.workerService = workerService;
    }

    @Scheduled(initialDelay = 3000L, fixedDelay = ClusterConstant.WORKER_CHECK_DELAY)
    public void workerCheck() {
        this.workerService.workerCheck();
    }
}
