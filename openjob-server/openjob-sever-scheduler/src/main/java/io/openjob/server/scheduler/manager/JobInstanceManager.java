package io.openjob.server.scheduler.manager;

import io.openjob.server.scheduler.dto.JobInstanceStopRequestDTO;
import io.openjob.server.scheduler.dto.JobInstanceStopResponseDTO;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class JobInstanceManager {
    public JobInstanceStopResponseDTO stop(JobInstanceStopRequestDTO stopRequest) {
        return new JobInstanceStopResponseDTO();
    }
}
