package io.openjob.server.scheduler;

import io.openjob.server.scheduler.autoconfigure.SchedulerProperties;
import io.openjob.server.scheduler.scheduler.DelayListScheduler;
import io.openjob.server.scheduler.scheduler.DelayZsetScheduler;
import io.openjob.server.scheduler.wheel.WheelManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class Scheduler {
    private final WheelManager wheelManager;
    private final DelayZsetScheduler delayZsetScheduler;
    private final DelayListScheduler delayListScheduler;
    private final SchedulerProperties schedulerProperties;

    @Autowired
    public Scheduler(WheelManager wheelManager, DelayZsetScheduler delayZsetScheduler, DelayListScheduler delayListScheduler, SchedulerProperties schedulerProperties) {
        this.wheelManager = wheelManager;
        this.delayZsetScheduler = delayZsetScheduler;
        this.delayListScheduler = delayListScheduler;
        this.schedulerProperties = schedulerProperties;
    }

    /**
     * Start
     */
    public void start() {
        // Timing wheel manager.
        this.wheelManager.start();

        // Enable delay.
        if (this.schedulerProperties.getDelay().getEnable()) {
            // Delay zset scheduler.
            this.delayZsetScheduler.start();

            // Delay list scheduler.
            this.delayListScheduler.start();
        }
    }

    /**
     * Refresh scheduler.
     *
     * @param slotsIds remove slot ids.
     */
    public void refresh(Set<Long> slotsIds) {
        // Remove by slot ids from timing wheel.
        if (!slotsIds.isEmpty()) {
            this.wheelManager.removeBySlotsId(slotsIds);
        }

        // Enable delay.
        if (this.schedulerProperties.getDelay().getEnable()) {
            // Delay zset scheduler.
            this.delayZsetScheduler.refresh();

            // Delay list scheduler.
            this.delayListScheduler.refresh();
        }
    }
}
