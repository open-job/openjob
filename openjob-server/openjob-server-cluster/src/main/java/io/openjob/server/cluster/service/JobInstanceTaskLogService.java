package io.openjob.server.cluster.service;

import io.openjob.common.request.WorkerJobInstanceTaskLogRequest;
import io.openjob.server.cluster.dto.WorkerJobInstanceTaskLogReqDTO;
import io.openjob.server.cluster.manager.JobInstanceTaskLogManager;
import io.openjob.server.common.util.BeanMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
@Service
public class JobInstanceTaskLogService {
    private final JobInstanceTaskLogManager jobInstanceTaskLogManager;

    @Autowired
    public JobInstanceTaskLogService(JobInstanceTaskLogManager jobInstanceTaskLogManager) {
        this.jobInstanceTaskLogManager = jobInstanceTaskLogManager;
    }


    /**
     * Handle instance log.
     *
     * @param logReq log request.
     */
    public void handleInstanceTaskLog(WorkerJobInstanceTaskLogRequest logReq) {
        this.jobInstanceTaskLogManager.handleInstanceTaskLog(BeanMapperUtil.map(logReq, WorkerJobInstanceTaskLogReqDTO.class));
    }
}
