package io.openjob.server.admin.service.impl;

import io.openjob.server.admin.request.AddJobRequest;
import io.openjob.server.admin.request.ListJobRequest;
import io.openjob.server.admin.request.UpdateJobRequest;
import io.openjob.server.admin.request.UpdateJobStatusRequest;
import io.openjob.server.admin.service.JobService;
import io.openjob.server.admin.vo.AddJobVO;
import io.openjob.server.admin.vo.ListJobVO;
import io.openjob.server.admin.vo.UpdateJobStatusVO;
import io.openjob.server.admin.vo.UpdateJobVO;
import org.springframework.stereotype.Component;

/**
 * @author zhenghongyang <sakuraovq@gmail.com>
 * @date 2022-11-13 18:21:16
 */
@Component
public class JobServiceImpl implements JobService {


    @Override
    public AddJobVO add(AddJobRequest addJobRequest) {
        return null;
    }

    @Override
    public UpdateJobVO update(UpdateJobRequest updateJobRequest) {
        return null;
    }

    @Override
    public UpdateJobStatusVO updateStatus(UpdateJobStatusRequest updateJobStatusRequest) {
        return null;
    }

    @Override
    public ListJobVO list(ListJobRequest listJobRequest) {
        return null;
    }
}
