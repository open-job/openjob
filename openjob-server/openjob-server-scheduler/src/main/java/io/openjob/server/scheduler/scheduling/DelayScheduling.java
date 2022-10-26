package io.openjob.server.scheduler.scheduling;

import io.openjob.server.scheduler.constant.SchedulerConstant;
import io.openjob.server.scheduler.service.DelaySchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class DelayScheduling {
    private final DelaySchedulingService delaySchedulingService;

    @Autowired
    public DelayScheduling(DelaySchedulingService delaySchedulingService) {
        this.delaySchedulingService = delaySchedulingService;
    }

    @Scheduled(initialDelay = SchedulerConstant.JOB_INITIAL_DELAY, fixedDelay = SchedulerConstant.JOB_FIXED_DELAY)
    public void delayJob() {
        this.delaySchedulingService.delayJob();
    }
}
