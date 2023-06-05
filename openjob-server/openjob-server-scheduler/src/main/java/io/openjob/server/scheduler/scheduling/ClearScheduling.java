package io.openjob.server.scheduler.scheduling;

import io.openjob.server.scheduler.service.ClearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.2
 */
@Component
public class ClearScheduling {
    private final ClearService clearService;

    @Autowired
    public ClearScheduling(ClearService clearService) {
        this.clearService = clearService;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void clearData() {
        this.clearService.clearData();
    }
}
