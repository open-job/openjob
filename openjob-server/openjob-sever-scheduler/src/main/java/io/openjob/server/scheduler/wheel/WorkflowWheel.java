package io.openjob.server.scheduler.wheel;

import io.openjob.common.util.RuntimeUtil;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class WorkflowWheel extends AbstractWheel {
    @Override
    public void start() {
        int coreCpu = RuntimeUtil.processors();
        this.createWheel(coreCpu, "workflow");
    }

    @Override
    public void stop() {
        this.shutdownWheel("workflow");
    }
}
