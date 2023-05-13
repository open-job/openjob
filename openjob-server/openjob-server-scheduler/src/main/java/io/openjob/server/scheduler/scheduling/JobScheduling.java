package io.openjob.server.scheduler.scheduling;

import io.openjob.server.scheduler.constant.SchedulerConstant;
import io.openjob.server.scheduler.dto.JobExecuteRequestDTO;
import io.openjob.server.scheduler.dto.JobExecuteResponseDTO;
import io.openjob.server.scheduler.service.JobSchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Component
public class JobScheduling {
    private final JobSchedulingService jobSchedulingService;

    @Autowired
    public JobScheduling(JobSchedulingService jobSchedulingService) {
        this.jobSchedulingService = jobSchedulingService;
    }

    @Scheduled(initialDelay = SchedulerConstant.JOB_INITIAL_DELAY, fixedDelay = SchedulerConstant.JOB_FIXED_DELAY)
    public void scheduleJob() {
        this.jobSchedulingService.scheduleJob();
    }

    @Scheduled(initialDelay = SchedulerConstant.JOB_INITIAL_DELAY, fixedDelay = SchedulerConstant.JOB_FIXED_DELAY)
    public void scheduleFailoverJob() {
        this.jobSchedulingService.scheduleFailoverJob();
    }
}
