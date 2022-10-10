package io.openjob.server.scheduler.data;

import io.openjob.server.repository.dao.DelayDAO;
import io.openjob.server.repository.entity.Delay;
import io.openjob.server.scheduler.util.CacheUtil;
import io.openjob.server.scheduler.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class DelayData {
    private final DelayDAO delayDAO;

    @Autowired
    public DelayData(DelayDAO delayDAO) {
        this.delayDAO = delayDAO;
    }

    public Delay getDelay(Long namespaceId, String topic) {
        String delayKey = CacheUtil.getDelayKey(namespaceId, topic);
        return RedisUtil.orElseGet(delayKey, () -> {
            Delay delay = this.delayDAO.findByNamespaceIdAndTopic(namespaceId, topic);
            if (Objects.isNull(delay)) {
                return new Delay();
            }
            return delay;
        }, Duration.ofDays(1));
    }
}
