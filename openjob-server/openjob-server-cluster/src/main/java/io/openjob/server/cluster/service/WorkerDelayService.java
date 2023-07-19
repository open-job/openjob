package io.openjob.server.cluster.service;

import io.openjob.common.request.WorkerDelayStatusRequest;
import io.openjob.server.cluster.executor.WorkerDelayStatusExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Service
public class WorkerDelayService {

    private final WorkerDelayStatusExecutor workerDelayStatusExecutor;

    @Autowired
    public WorkerDelayService(WorkerDelayStatusExecutor workerDelayStatusExecutor) {
        this.workerDelayStatusExecutor = workerDelayStatusExecutor;
    }

    /**
     * Handle delay status.
     *
     * @param workerDelayStatusRequest workerDelayStatusRequest
     */
    public void handleDelayStatus(WorkerDelayStatusRequest workerDelayStatusRequest) {
        this.workerDelayStatusExecutor.submit(workerDelayStatusRequest);
    }
}
