package io.openjob.server.admin.service;

import io.openjob.server.admin.request.job.AddJobRequest;
import io.openjob.server.admin.request.job.ListJobRequest;
import io.openjob.server.admin.request.job.UpdateJobRequest;
import io.openjob.server.admin.request.job.UpdateJobStatusRequest;
import io.openjob.server.admin.vo.job.AddJobVO;
import io.openjob.server.admin.vo.job.ListJobVO;
import io.openjob.server.admin.vo.job.UpdateJobStatusVO;
import io.openjob.server.admin.vo.job.UpdateJobVO;

/**
 * @author zhenghongyang <sakuraovq@gmail.com>
 * @date 2022-11-13 18:16:47
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
     * Add job.
     *
     * @param updateJobStatusRequest UpdateJobStatusRequest
     * @return UpdateJobStatusVO
     */
    UpdateJobStatusVO updateStatus(UpdateJobStatusRequest updateJobStatusRequest);

    /**
     * Add job.
     *
     * @param listJobRequest ListJobRequest
     * @return ListJobVO
     */
    ListJobVO list(ListJobRequest listJobRequest);
}
