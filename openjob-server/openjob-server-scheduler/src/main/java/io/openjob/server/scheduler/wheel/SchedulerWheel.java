package io.openjob.server.scheduler.wheel;

import io.openjob.common.util.RuntimeUtil;
import org.springframework.stereotype.Component;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Component
public class SchedulerWheel extends AbstractWheel {
    @Override
    public void start() {
        int coreCpu = RuntimeUtil.processors();
        this.createWheel(coreCpu, "scheduler");
    }

    @Override
    public void stop() {
        this.shutdownWheel("scheduler");
    }
}
