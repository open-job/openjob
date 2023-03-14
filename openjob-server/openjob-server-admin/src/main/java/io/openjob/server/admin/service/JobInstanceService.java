package io.openjob.server.admin.service;

import io.openjob.server.admin.request.job.DeleteJobInstanceRequest;
import io.openjob.server.admin.request.job.ListJobInstanceRequest;
import io.openjob.server.admin.request.job.ListProcessorLogRequest;
import io.openjob.server.admin.request.job.StopJobInstanceRequest;
import io.openjob.server.admin.vo.job.DeleteJobInstanceVO;
import io.openjob.server.admin.vo.job.ListJobInstanceVO;
import io.openjob.server.admin.vo.job.ListProcessorLogVO;
import io.openjob.server.admin.vo.job.StopJobInstanceVO;
import io.openjob.server.common.vo.PageVO;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface JobInstanceService {

    /**
     * List job instance.
     *
     * @param request request
     * @return PageVO
     */
    PageVO<ListJobInstanceVO> getPageList(ListJobInstanceRequest request);

    /**
     * Kill instance
     *
     * @param killRequest killRequest
     * @return return
     */
    StopJobInstanceVO stopInstance(StopJobInstanceRequest killRequest);

    /**
     * Delete job instance.
     *
     * @param request request
     * @return DeleteJobInstanceVO
     */
    DeleteJobInstanceVO deleteInstance(DeleteJobInstanceRequest request);

    /**
     * Get processor log.
     *
     * @param request request
     * @return ListProcessorLogVO
     */
    ListProcessorLogVO getProcessorList(ListProcessorLogRequest request);
}
