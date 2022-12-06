package io.openjob.server.cluster.scheduling;

import io.openjob.server.cluster.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class HealthScheduling {
    private final HealthService healthService;

    @Autowired
    public HealthScheduling(HealthService healthService) {
        this.healthService = healthService;
    }

    @Scheduled(initialDelay = 3000L, fixedDelay = 3000L)
    public void healthCheck() {
        healthService.check();
    }
}
