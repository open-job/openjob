package io.openjob.server.scheduler.factory;

import io.openjob.server.scheduler.scheduler.DelayAddListScheduler;
import io.openjob.server.scheduler.scheduler.DelayDeleteListScheduler;
import io.openjob.server.scheduler.scheduler.DelayStatusListScheduler;
import io.openjob.server.scheduler.scheduler.DelayZsetScheduler;
import io.openjob.server.scheduler.wheel.WheelManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Log4j2
@Component
public class SchedulerFactoryBean implements DisposableBean {
    private final WheelManager wheelManager;
    private final DelayZsetScheduler delayZsetScheduler;
    private final DelayAddListScheduler delayAddListScheduler;
    private final DelayStatusListScheduler delayStatusListScheduler;
    private final DelayDeleteListScheduler delayDeleteListScheduler;

    @Autowired
    public SchedulerFactoryBean(WheelManager wheelManager,
                                DelayZsetScheduler delayZsetScheduler,
                                DelayAddListScheduler delayAddListScheduler,
                                DelayStatusListScheduler delayStatusListScheduler,
                                DelayDeleteListScheduler delayDeleteListScheduler) {
        this.wheelManager = wheelManager;
        this.delayZsetScheduler = delayZsetScheduler;
        this.delayAddListScheduler = delayAddListScheduler;
        this.delayStatusListScheduler = delayStatusListScheduler;
        this.delayDeleteListScheduler = delayDeleteListScheduler;
    }

    @Override
    public void destroy() {
        // Timing wheel manager.
        this.wheelManager.stop();

        // Delay zset scheduler.
        this.delayZsetScheduler.stop();

        // Delay add list scheduler.
        this.delayAddListScheduler.stop();

        // Delay add list scheduler.
        this.delayStatusListScheduler.stop();

        // Delay add list scheduler.
        this.delayDeleteListScheduler.stop();

        log.info("Scheduler shutdown!");
    }
}
