package io.openjob.server.openapi.service;

import io.openjob.server.openapi.request.WorkerJobInstanceTaskLogRequest;
import io.openjob.server.openapi.vo.WorkerJobInstanceTaskLogVO;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.8
 */
public interface OpenTaskLogService {

    /**
     * Batch add
     *
     * @param logRequest logRequest
     * @return WorkerJobInstanceTaskLogVO
     */
    WorkerJobInstanceTaskLogVO batchAdd(WorkerJobInstanceTaskLogRequest logRequest);
}
