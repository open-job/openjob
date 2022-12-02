package io.openjob.server.scheduler.data;

import io.openjob.server.repository.dao.DelayDAO;
import io.openjob.server.repository.entity.Delay;
import io.openjob.server.scheduler.constant.CacheConst;
import io.openjob.server.scheduler.util.RedisUtil;
import io.openjob.server.scheduler.util.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    /**
     * Get delay by id.
     *
     * @param topic topic
     * @return Delay
     */
    public Delay getDelay(String topic) {
        String delayKey = CacheUtil.getDelayKey(topic);
        return RedisUtil.orElseGet(delayKey, () -> {
            Delay delay = this.delayDAO.findByTopic(topic);
            if (Objects.isNull(delay)) {
                return new Delay();
            }
            return delay;
        }, Duration.ofDays(1));
    }

    /**
     * Get delay list.
     *
     * @param topics topics
     * @return List
     */
    public List<Delay> getDelayList(List<String> topics) {
        return RedisUtil.multiOrElseGet(
                CacheConst.DELAY_PREFIX,
                topics,
                t -> this.delayDAO.findByTopics(t)
                        .stream()
                        .collect(Collectors.toMap(Delay::getTopic, v -> v)),
                Duration.ofDays(1)
        );
    }
}
