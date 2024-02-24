package io.openjob.server.admin.service;

import io.openjob.server.admin.request.job.AddJobRequest;
import io.openjob.server.admin.request.job.DeleteJobRequest;
import io.openjob.server.admin.request.job.ExecuteJobRequest;
import io.openjob.server.admin.request.job.ListJobRequest;
import io.openjob.server.admin.request.job.TimeExpressionRequest;
import io.openjob.server.admin.request.job.UpdateJobRequest;
import io.openjob.server.admin.request.job.UpdateJobStatusRequest;
import io.openjob.server.admin.vo.job.AddJobVO;
import io.openjob.server.admin.vo.job.DeleteJobVO;
import io.openjob.server.admin.vo.job.ExecuteJobVO;
import io.openjob.server.admin.vo.job.ListJobVO;
import io.openjob.server.admin.vo.job.TimeExpressionVO;
import io.openjob.server.admin.vo.job.UpdateJobStatusVO;
import io.openjob.server.admin.vo.job.UpdateJobVO;
import io.openjob.server.common.vo.PageVO;

/**
 * @author zhenghongyang sakuraovq@gmail.com
 * @since 1.0.0
 */
public interface JobService {

    /**
     * Add job.
     *
     * @param addJobRequest AddJobRequest
     * @return AddJobResponse
     */
    AddJobVO add(AddJobRequest addJobRequest);

    /**
     * update job.
     *
     * @param updateJobRequest UpdateJobRequest
     * @return UpdateJobVO
     */
    UpdateJobVO update(UpdateJobRequest updateJobRequest);

    /**
     * Delete job
     *
     * @param deleteJobRequest deleteJobRequest
     * @return DeleteJobVO
     */
    DeleteJobVO delete(DeleteJobRequest deleteJobRequest);

    /**
     * Add job.
     *
     * @param updateJobStatusRequest UpdateJobStatusRequest
     * @return UpdateJobStatusVO
     */
    UpdateJobStatusVO updateStatus(UpdateJobStatusRequest updateJobStatusRequest);

    /**
     * Execute job
     *
     * @param request request
     * @return ExecuteJobVO
     */
    ExecuteJobVO execute(ExecuteJobRequest request);

    /**
     * Time expression
     *
     * @param request request
     * @return TimeExpressionVO
     */
    TimeExpressionVO timeExpression(TimeExpressionRequest request);

    /**
     * Add job.
     *
     * @param listJobRequest ListJobRequest
     * @return ListJobVO
     */
    PageVO<ListJobVO> getPageList(ListJobRequest listJobRequest);
}
