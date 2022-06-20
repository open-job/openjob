package io.openjob.server.cluster.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class HealthScheduler {
    @Scheduled(fixedRate = 3 * 1000L)
    public void healthCheck() {
//        check();
    }

    private void check() {

    }
}
