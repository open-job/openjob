package io.openjob.server.scheduler.scheduler;

import io.openjob.server.scheduler.dto.JobStartRequestDTO;
import io.openjob.server.scheduler.dto.JobStartResponseDTO;
import io.openjob.server.scheduler.dto.JobStopRequestDTO;
import io.openjob.server.scheduler.dto.JobStopResponseDTO;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class JobScheduler {
    public JobStartResponseDTO start(JobStartRequestDTO startRequest) {
        return new JobStartResponseDTO();
    }

    public JobStopResponseDTO stop(JobStopRequestDTO stopRequest) {
        return new JobStopResponseDTO();
    }
}
