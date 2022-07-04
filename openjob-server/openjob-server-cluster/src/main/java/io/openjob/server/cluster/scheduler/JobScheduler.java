package io.openjob.server.cluster.scheduler;

import io.openjob.server.cluster.service.JobSchedulerService;
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

    @Scheduled(initialDelay = 3000L, fixedDelay = 60000L)
    public void scheduleJob() {
        this.jobSchedulerService.scheduleJob();
    }
}
