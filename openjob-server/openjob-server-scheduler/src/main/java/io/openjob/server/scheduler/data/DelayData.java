package io.openjob.server.scheduler.data;

import com.google.common.collect.Lists;
import io.openjob.server.repository.dao.DelayDAO;
import io.openjob.server.repository.entity.Delay;
import io.openjob.server.scheduler.constant.CacheConst;
import io.openjob.server.scheduler.dto.DelayInstanceAddRequestDTO;
import io.openjob.server.scheduler.util.RedisUtil;
import io.openjob.server.scheduler.util.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.Collections;
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
        String delayKey = CacheUtil.getDelayDetailTopicKey(topic);
        return RedisUtil.orElseGet(delayKey, () -> {
            Delay delay = this.delayDAO.findByTopic(topic);
            if (Objects.isNull(delay)) {
                return new Delay();
            }
            return delay;
        }, Duration.ofDays(1));
    }

    public Delay getDelayById(Long id) {
        String delayKey = CacheUtil.getDelayDetailIdKey(id);
        return RedisUtil.orElseGet(delayKey, () -> {
            Delay delay = this.delayDAO.findById(id)
                    .orElseThrow(() -> new RuntimeException(String.format("Delay is not exist(%d)!", id)));
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
                CacheConst.DELAY_DETAIL_TOPIC_PREFIX,
                topics,
                t -> this.delayDAO.findByTopics(t)
                        .stream()
                        .collect(Collectors.toMap(Delay::getTopic, v -> v)),
                Duration.ofDays(1)
        );
    }

    /**
     * Get list by appid.
     *
     * @param appId appid.
     * @return list.
     */
    public List<Delay> getListByAppId(Long appId) {
        return this.delayDAO.findByAppId(appId);
    }

    /**
     * Get delay instance list.
     *
     * @param taskIds task ids.
     * @return list.
     */
    public List<DelayInstanceAddRequestDTO> getDelayInstanceList(List<String> taskIds) {
        List<String> cacheKeys = taskIds.stream().map(CacheUtil::getDelayDetailTaskIdKey)
                .collect(Collectors.toList());

        // Multi to get detail.
        List<Object> cacheList = RedisUtil.getTemplate().opsForValue().multiGet(cacheKeys);
        if (CollectionUtils.isEmpty(cacheList)) {
            return Collections.emptyList();
        }

        // Delay detail list.
        List<DelayInstanceAddRequestDTO> detailList = Lists.newArrayList();
        for (Object detail : cacheList) {
            if (Objects.isNull(detail)) {
                continue;
            }

            if (detail instanceof DelayInstanceAddRequestDTO) {
                detailList.add((DelayInstanceAddRequestDTO) detail);
            }
        }
        return detailList;
    }
}
