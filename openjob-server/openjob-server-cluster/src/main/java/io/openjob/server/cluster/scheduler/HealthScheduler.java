package io.openjob.server.cluster.scheduler;

import io.openjob.server.cluster.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class HealthScheduler {
    private final HealthService healthService;

    @Autowired
    public HealthScheduler(HealthService healthService) {
        this.healthService = healthService;
    }

    @Scheduled(initialDelay = 3000L, fixedDelay = 2000L)
    public void healthCheck() {
        healthService.check();
    }
}
