package io.openjob.server.scheduler;

import io.openjob.server.scheduler.autoconfigure.SchedulerProperties;
import io.openjob.server.scheduler.scheduler.DelayAddListScheduler;
import io.openjob.server.scheduler.scheduler.DelayDeleteListScheduler;
import io.openjob.server.scheduler.scheduler.DelayStatusListScheduler;
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
    private final DelayAddListScheduler delayAddListScheduler;
    private final DelayStatusListScheduler delayStatusListScheduler;
    private final DelayDeleteListScheduler delayDeleteListScheduler;
    private final SchedulerProperties schedulerProperties;

    @Autowired
    public Scheduler(WheelManager wheelManager,
                     DelayZsetScheduler delayZsetScheduler,
                     DelayAddListScheduler delayAddListScheduler,
                     DelayStatusListScheduler delayStatusListScheduler,
                     DelayDeleteListScheduler delayDeleteListScheduler,
                     SchedulerProperties schedulerProperties) {
        this.wheelManager = wheelManager;
        this.delayZsetScheduler = delayZsetScheduler;
        this.delayAddListScheduler = delayAddListScheduler;
        this.delayStatusListScheduler = delayStatusListScheduler;
        this.delayDeleteListScheduler = delayDeleteListScheduler;
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

            // Delay add list scheduler.
            this.delayAddListScheduler.start();

            // Delay status list scheduler.
            this.delayStatusListScheduler.start();

            // Delay delete list scheduler.
            this.delayDeleteListScheduler.start();
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

            // Delay add list scheduler.
            this.delayAddListScheduler.refresh();

            // Delay status list scheduler.
            this.delayStatusListScheduler.refresh();

            // Delay delete list scheduler.
            this.delayDeleteListScheduler.refresh();
        }
    }
}
