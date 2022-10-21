package io.openjob.server.scheduler.manager;

import io.openjob.server.scheduler.dto.JobStartRequestDTO;
import io.openjob.server.scheduler.dto.JobStartResponseDTO;
import io.openjob.server.scheduler.dto.JobStopRequestDTO;
import io.openjob.server.scheduler.dto.JobStopResponseDTO;
import lombok.Data;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class JobManager {
    public JobStartResponseDTO start(JobStartRequestDTO startRequest) {
        return new JobStartResponseDTO();
    }

    public JobStopResponseDTO stop(JobStopRequestDTO stopRequest) {
        return new JobStopResponseDTO();
    }
}
