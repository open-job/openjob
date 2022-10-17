package io.openjob.server.scheduler.scheduler;

import io.openjob.server.scheduler.autoconfigure.SchedulerProperties;
import io.openjob.server.scheduler.constant.SchedulerConstant;
import io.openjob.server.scheduler.service.DelayService;
import io.openjob.server.scheduler.service.JobSchedulerService;
import io.openjob.server.scheduler.service.WorkflowService;
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

    private final DelayService delayService;

    private final WorkflowService workflowService;

    private final SchedulerProperties schedulerProperties;

    @Autowired
    public JobScheduler(JobSchedulerService jobSchedulerService, DelayService delayService, WorkflowService workflowService, SchedulerProperties schedulerProperties) {
        this.jobSchedulerService = jobSchedulerService;
        this.delayService = delayService;
        this.workflowService = workflowService;
        this.schedulerProperties = schedulerProperties;
    }

    /**
     * Schedule job.
     */
    @Scheduled(initialDelay = SchedulerConstant.JOB_INITIAL_DELAY, fixedDelay = SchedulerConstant.JOB_FIXED_DELAY)
    public void scheduleJob() {
        this.jobSchedulerService.scheduleJob();
    }

    /**
     * Delay job.
     */
    @Scheduled(initialDelay = SchedulerConstant.JOB_INITIAL_DELAY, fixedDelay = SchedulerConstant.JOB_FIXED_DELAY)
    public void delayJob() {
        if (this.schedulerProperties.getDelay().getEnable()) {
            this.delayService.delayJob();
        }
    }

    /**
     * Workflow job.
     */
    @Scheduled(initialDelay = SchedulerConstant.JOB_INITIAL_DELAY, fixedDelay = SchedulerConstant.JOB_FIXED_DELAY)
    public void workflowJob() {
        this.workflowService.workflowJob();
    }
}
