package io.openjob.server.scheduler.scheduler;

import io.openjob.server.scheduler.data.DelayData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
@Component
public class DelayScheduler {
    private final DelayData delayData;

    @Autowired
    public DelayScheduler(DelayData delayData) {
        this.delayData = delayData;
    }

    /**
     * Refresh delay version.
     *
     * @param topic       topic
     * @param id          id
     * @param failDelayId failDelayId
     */
    public void refreshDelayCache(String topic, Long id, Long failDelayId) {
        // Delete cache
        this.delayData.deleteByTopicOrId(topic, id, failDelayId);
    }
}
