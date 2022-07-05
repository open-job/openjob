package io.openjob.server.scheduler.factory;

import io.openjob.server.scheduler.Scheduler;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Log4j2
@Component
public class SchedulerFactoryBean implements DisposableBean {
    @Override
    public void destroy() throws Exception {
        Scheduler.stop();

        log.info("Scheduler shutdown!");
    }
}
