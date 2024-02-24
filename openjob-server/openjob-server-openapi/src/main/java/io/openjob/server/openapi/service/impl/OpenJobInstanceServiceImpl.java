package io.openjob.server.openapi.service.impl;

import io.openjob.server.cluster.dto.WorkerJobInstanceStatusReqDTO;
import io.openjob.server.cluster.dto.WorkerJobInstanceStatusRespDTO;
import io.openjob.server.cluster.dto.WorkerJobInstanceTaskBatchReqDTO;
import io.openjob.server.cluster.dto.WorkerJobInstanceTaskBatchRespDTO;
import io.openjob.server.cluster.manager.JobInstanceManager;
import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.openapi.request.WorkerJobInstanceStatusRequest;
import io.openjob.server.openapi.request.WorkerJobInstanceTaskBatchRequest;
import io.openjob.server.openapi.service.OpenJobInstanceService;
import io.openjob.server.openapi.vo.WorkerJobInstanceStatusVO;
import io.openjob.server.openapi.vo.WorkerJobInstanceTaskBatchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.8
 */
@Service
public class OpenJobInstanceServiceImpl implements OpenJobInstanceService {

    private final JobInstanceManager jobInstanceManager;

    @Autowired
    public OpenJobInstanceServiceImpl(JobInstanceManager jobInstanceManager) {
        this.jobInstanceManager = jobInstanceManager;
    }

    @Override
    public WorkerJobInstanceStatusVO handleStatus(WorkerJobInstanceStatusRequest statusRequest) {
        WorkerJobInstanceStatusRespDTO respDTO = this.jobInstanceManager.handleInstanceStatus(BeanMapperUtil.map(statusRequest, WorkerJobInstanceStatusReqDTO.class));
        return BeanMapperUtil.map(respDTO, WorkerJobInstanceStatusVO.class);
    }

    @Override
    public WorkerJobInstanceTaskBatchVO handleTasks(WorkerJobInstanceTaskBatchRequest batchRequest) {
        WorkerJobInstanceTaskBatchRespDTO respDTO = this.jobInstanceManager.handleInstanceTasks(BeanMapperUtil.map(batchRequest, WorkerJobInstanceTaskBatchReqDTO.class));
        return BeanMapperUtil.map(respDTO, WorkerJobInstanceTaskBatchVO.class);
    }
}
