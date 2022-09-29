package io.openjob.server.scheduler.wheel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class WheelManager {
    private final DelayWheel delayWheel;
    private final SchedulerWheel schedulerWheel;
    private final WorkflowWheel workflowWheel;

    @Autowired
    public WheelManager(DelayWheel delayWheel, SchedulerWheel schedulerWheel, WorkflowWheel workflowWheel) {
        this.delayWheel = delayWheel;
        this.schedulerWheel = schedulerWheel;
        this.workflowWheel = workflowWheel;
    }

    public void start() {
        // Scheduler wheel
        this.schedulerWheel.start();

        // Delay wheel
        this.delayWheel.start();

        // Workflow wheel
        this.workflowWheel.start();
    }

    public void removeBySlotsId(Set<Long> slotsIds) {
        // Scheduler wheel
        this.schedulerWheel.removeBySlotsId(slotsIds);

        // Delay wheel
        this.delayWheel.removeBySlotsId(slotsIds);

        // Workflow wheel
        this.workflowWheel.removeBySlotsId(slotsIds);
    }

    public void stop() {
        // Scheduler wheel
        this.schedulerWheel.stop();

        // Delay wheel
        this.delayWheel.stop();

        // Workflow wheel
        this.workflowWheel.stop();
    }
}
