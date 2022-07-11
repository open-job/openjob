package io.openjob.server.scheduler.scheduler;

import io.openjob.server.scheduler.constant.SchedulerConstant;
import io.openjob.server.scheduler.service.JobSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class JobScheduler {

    private final JobSchedulerService jobSchedulerService;

    @Autowired
    public JobScheduler(JobSchedulerService jobSchedulerService) {
        this.jobSchedulerService = jobSchedulerService;
    }

    @Scheduled(initialDelay = SchedulerConstant.JOB_INITIAL_DELAY, fixedDelay = SchedulerConstant.JOB_FIXED_DELAY)
    public void scheduleJob() {
        this.jobSchedulerService.scheduleJob();
    }
}
