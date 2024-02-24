package io.openjob.server.cluster.service;

import io.openjob.common.request.WorkerJobInstanceStatusRequest;
import io.openjob.common.request.WorkerJobInstanceTaskBatchRequest;
import io.openjob.server.cluster.dto.WorkerJobInstanceStatusReqDTO;
import io.openjob.server.cluster.dto.WorkerJobInstanceTaskBatchReqDTO;
import io.openjob.server.cluster.manager.JobInstanceManager;
import io.openjob.server.common.util.BeanMapperUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Service
@Log4j2
public class JobInstanceService {
    private final JobInstanceManager jobInstanceManager;

    @Autowired
    public JobInstanceService(JobInstanceManager jobInstanceManager) {
        this.jobInstanceManager = jobInstanceManager;
    }

    public void handleInstanceStatus(WorkerJobInstanceStatusRequest statusRequest) {
        this.jobInstanceManager.handleInstanceStatus(BeanMapperUtil.map(statusRequest, WorkerJobInstanceStatusReqDTO.class));
    }


    public void handleInstanceTasks(WorkerJobInstanceTaskBatchRequest taskBatchRequest) {
        this.jobInstanceManager.handleInstanceTasks(BeanMapperUtil.map(taskBatchRequest, WorkerJobInstanceTaskBatchReqDTO.class));
    }
}
