package io.openjob.server.scheduler.factory;

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

    @Autowired
    public SchedulerFactoryBean(WheelManager wheelManager, DelayZsetScheduler delayZsetScheduler) {
        this.wheelManager = wheelManager;
        this.delayZsetScheduler = delayZsetScheduler;
    }

    @Override
    public void destroy() {
        // Timing wheel manager.
        this.wheelManager.stop();

        // Delay zset scheduler.
        this.delayZsetScheduler.stop();

        log.info("Scheduler shutdown!");
    }
}
