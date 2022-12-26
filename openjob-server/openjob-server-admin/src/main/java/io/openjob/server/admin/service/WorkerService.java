package io.openjob.server.admin.service;

import io.openjob.server.admin.request.worker.WorkerListRequest;
import io.openjob.server.admin.vo.worker.WorkerListVO;
import io.openjob.server.common.dto.PageDTO;

/**
 * @author riki
 * @date 2022-12-26
 */
public interface WorkerService {

    /**
     * List Worker by page
     * @param request params
     * @return result
     */
    PageDTO<WorkerListVO> page(WorkerListRequest request);
}
