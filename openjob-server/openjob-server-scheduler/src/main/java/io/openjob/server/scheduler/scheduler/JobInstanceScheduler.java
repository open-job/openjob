package io.openjob.server.scheduler.scheduler;

import io.openjob.server.scheduler.dto.JobInstanceStopRequestDTO;
import io.openjob.server.scheduler.dto.JobInstanceStopResponseDTO;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class JobInstanceScheduler {
    public JobInstanceStopResponseDTO stop(JobInstanceStopRequestDTO stopRequest) {
        return new JobInstanceStopResponseDTO();
    }
}
