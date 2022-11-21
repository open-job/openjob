package io.openjob.server.scheduler.wheel;

import io.openjob.server.scheduler.service.DelaySchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class WheelManager {
    private final SchedulerWheel schedulerWheel;
    private final WorkflowWheel workflowWheel;
    private final DelaySchedulingService delaySchedulingService;

    @Autowired
    public WheelManager(SchedulerWheel schedulerWheel, WorkflowWheel workflowWheel, DelaySchedulingService delaySchedulingService) {
        this.schedulerWheel = schedulerWheel;
        this.workflowWheel = workflowWheel;
        this.delaySchedulingService = delaySchedulingService;
    }

    /**
     * Start
     */
    public void start() {
        // Scheduler wheel
        this.schedulerWheel.start();

        // Workflow wheel
        this.workflowWheel.start();

        this.delaySchedulingService.start();
    }

    /**
     * Remove by slot id.
     *
     * @param slotsIds slot ids.
     */
    public void removeBySlotsId(Set<Long> slotsIds) {
        // Scheduler wheel
        this.schedulerWheel.removeBySlotsId(slotsIds);

        // Workflow wheel
        this.workflowWheel.removeBySlotsId(slotsIds);
    }

    /**
     * Stop
     */
    public void stop() {
        // Scheduler wheel
        this.schedulerWheel.stop();

        // Workflow wheel
        this.workflowWheel.stop();
    }
}
