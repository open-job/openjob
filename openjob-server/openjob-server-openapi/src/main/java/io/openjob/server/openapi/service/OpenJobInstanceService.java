package io.openjob.server.openapi.service;

import io.openjob.server.openapi.request.WorkerJobInstanceStatusRequest;
import io.openjob.server.openapi.request.WorkerJobInstanceTaskBatchRequest;
import io.openjob.server.openapi.vo.WorkerJobInstanceStatusVO;
import io.openjob.server.openapi.vo.WorkerJobInstanceTaskBatchVO;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.8
 */
public interface OpenJobInstanceService {

    /**
     * Handle status
     *
     * @param statusRequest statusRequest
     * @return WorkerJobInstanceStatusVO
     */
    WorkerJobInstanceStatusVO handleStatus(WorkerJobInstanceStatusRequest statusRequest);

    /**
     * Handle tasks
     *
     * @param batchRequest batchRequest
     * @return WorkerJobInstanceTaskBatchVO
     */
    WorkerJobInstanceTaskBatchVO handleTasks(WorkerJobInstanceTaskBatchRequest batchRequest);
}
