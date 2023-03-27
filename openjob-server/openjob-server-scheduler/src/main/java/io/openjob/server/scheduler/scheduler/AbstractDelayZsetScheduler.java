package io.openjob.server.scheduler.scheduler;

import io.openjob.common.SpringContext;
import io.openjob.common.constant.CommonConstant;
import io.openjob.common.util.DateUtil;
import io.openjob.server.repository.entity.Delay;
import io.openjob.server.scheduler.constant.SchedulerConstant;
import io.openjob.server.scheduler.data.DelayData;
import io.openjob.server.scheduler.dto.DelayInstanceAddRequestDTO;
import io.openjob.server.scheduler.util.CacheUtil;
import io.openjob.server.scheduler.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
public abstract class AbstractDelayZsetScheduler extends AbstractDelayScheduler {
    public static abstract class AbstractZsetRunnable extends AbstractRunnable {
        private final DelayData delayData;

        /**
         * New ZsetRunnable.
         *
         * @param currentSlotId current slot id.
         */
        public AbstractZsetRunnable(Long currentSlotId) {
            super(currentSlotId);
            this.delayData = SpringContext.getBean(DelayData.class);
        }

        protected abstract Boolean isFailZset();

        protected abstract String getCacheKey(String topic);

        protected void push2FailZset(RedisOperations<String, Object> operations, String topic, List<DelayInstanceAddRequestDTO> list) {

        }

        protected void ignoreTaskList(RedisOperations<String, Object> operations, String topic, List<DelayInstanceAddRequestDTO> list) {

        }

        /**
         * Range delay instance.
         *
         * @param key zset cache key.
         * @throws InterruptedException interruptedException
         */
        protected void rangeDelayInstance(String key) throws InterruptedException {
            // Range delay instance from zset.
            Set<Object> rangeObjects = RedisUtil.getTemplate().opsForZSet().rangeByScore(key, 0, DateUtil.timestamp(), 0, 50);

            // Delay instance is empty.
            if (CollectionUtils.isEmpty(rangeObjects)) {
                Thread.sleep(500);
                return;
            }

            // Push to list and remove from zset
            this.pushAndRemoveDelayInstance(key, rangeObjects);
        }

        /**
         * Push to list and remove from zset
         *
         * @param key          zset cache key.
         * @param rangeObjects range objects.
         */
        @SuppressWarnings("unchecked")
        protected void pushAndRemoveDelayInstance(String key, Set<Object> rangeObjects) {
            // Timing zset members.
            List<String> timingMembers = rangeObjects.stream().map(String::valueOf)
                    .collect(Collectors.toList());

            // Retry times.
            // First retry times is zero.
            Map<String, Integer> timesMap = getTaskRetryTimes(timingMembers);

            // Get delay instance detail list
            List<DelayInstanceAddRequestDTO> detailList = this.delayData.getDelayInstanceList(timingMembers);

            // Group by topic.
            Map<String, List<DelayInstanceAddRequestDTO>> detailListMap = detailList.stream()
                    .collect(Collectors.groupingBy(DelayInstanceAddRequestDTO::getTopic));

            Map<String, List<Delay>> delayMap = this.delayData.getDelayList(new ArrayList<>(detailListMap.keySet())).stream()
                    .collect(Collectors.groupingBy(Delay::getTopic));

            Map<String, List<DelayInstanceAddRequestDTO>> push2FailZsetMap = new HashMap<>();
            Map<String, List<DelayInstanceAddRequestDTO>> push2TopicMap = new HashMap<>();
            Map<String, List<DelayInstanceAddRequestDTO>> ignoreMap = new HashMap<>();

            this.getFailAndTopicAndIgnoreMap(timesMap, detailListMap, delayMap, push2FailZsetMap, push2TopicMap, ignoreMap);

            // Execute by pipelined.
            RedisUtil.getTemplate().executePipelined(new SessionCallback<List<Object>>() {
                @Override
                public List<Object> execute(@Nonnull RedisOperations operations) throws DataAccessException {
                    operations.multi();
                    push2FailZsetMap.forEach((t, list) -> push2FailZset(operations, t, list));

                    push2TopicMap.forEach((t, list) -> {
                        // List cache key.
                        Delay topicDelay = delayMap.get(t).get(0);
                        push2ListAndIncScore(operations, key, timesMap, list, topicDelay);
                    });

                    ignoreMap.forEach((t, list) -> ignoreTaskList(operations, t, list));
                    operations.exec();
                    return null;
                }
            });
        }

        private void getFailAndTopicAndIgnoreMap(Map<String, Integer> timesMap, Map<String, List<DelayInstanceAddRequestDTO>> detailListMap, Map<String, List<Delay>> delayMap, Map<String, List<DelayInstanceAddRequestDTO>> push2FailZsetMap, Map<String, List<DelayInstanceAddRequestDTO>> push2TopicMap, Map<String, List<DelayInstanceAddRequestDTO>> ignoreMap) {
            // Push by topic
            detailListMap.forEach((t, list) -> {
                Delay topicDelay = delayMap.get(t).get(0);
                List<DelayInstanceAddRequestDTO> push2FailZset = new ArrayList<>();
                List<DelayInstanceAddRequestDTO> ignoreList = new ArrayList<>();
                List<DelayInstanceAddRequestDTO> pushTask = list.stream().filter(i -> {
                    Integer currentTimes = timesMap.get(i.getTaskId());

                    if (isFailZset()) {
                        return true;
                    }

                    // Enable fail topic
                    if (CommonConstant.YES.equals(topicDelay.getFailTopicEnable())) {
                        // Delay task and arrive retry times.
                        if (topicDelay.getPid().equals(0L) && currentTimes >= topicDelay.getFailRetryTimes()) {
                            push2FailZset.add(i);
                            return false;
                        }
                    } else {
                        if (topicDelay.getPid().equals(0L) && currentTimes >= topicDelay.getFailRetryTimes()) {
                            ignoreList.add(i);
                            return false;
                        }
                    }
                    return true;
                }).collect(Collectors.toList());

                if (!CollectionUtils.isEmpty(push2FailZset)) {
                    push2FailZsetMap.put(t, push2FailZset);
                }

                if (!CollectionUtils.isEmpty(pushTask)) {
                    push2TopicMap.put(t, pushTask);
                }

                if (!CollectionUtils.isEmpty(ignoreList)) {
                    ignoreMap.put(t, ignoreList);
                }
            });
        }

        private void push2ListAndIncScore(RedisOperations<String, Object> operations,
                                          String zsetKey,
                                          Map<String, Integer> timesMap,
                                          List<DelayInstanceAddRequestDTO> pushTask,
                                          Delay topicDelay) {
            if (CollectionUtils.isEmpty(pushTask)) {
                return;
            }

            String cacheListKey = getCacheKey(topicDelay.getTopic());

            // Remove task id.
            // Fixed retry task id.
            pushTask.forEach(i -> {
                operations.opsForList().remove(cacheListKey, 0, i.getTaskId());

                // Retry times.
                String delayRetryTimesKey = CacheUtil.getDelayRetryTimesKey(i.getTaskId());
                operations.opsForValue().increment(delayRetryTimesKey);
            });

            if (CollectionUtils.isEmpty(pushTask)) {
                return;
            }

            // Add task id to queue.
            operations.opsForList().rightPushAll(cacheListKey, pushTask.stream().map(DelayInstanceAddRequestDTO::getTaskId).toArray());

            // Update score(score=score+timeout)
            pushTask.forEach(d -> {
                int retryTimes = timesMap.get(d.getTaskId());
                double retryTime = topicDelay.getExecuteTimeout()
                        + (long) topicDelay.getFailRetryInterval() * retryTimes
                        + SchedulerConstant.DELAY_RETRY_AFTER;
                operations.opsForZSet().incrementScore(zsetKey, d.getTaskId(), retryTime);
            });
        }

        private Map<String, Integer> getTaskRetryTimes(List<String> timingMembers) {
            List<String> retryKeys = timingMembers.stream().map(CacheUtil::getDelayRetryTimesKey)
                    .collect(Collectors.toList());
            List<Object> times = RedisUtil.getTemplate().opsForValue().multiGet(retryKeys);
            Map<String, Integer> timesMap = new HashMap<>();
            for (int i = 0; i < retryKeys.size(); i++) {
                if (Objects.isNull(times)) {
                    continue;
                }
                timesMap.put(timingMembers.get(i), (Integer) Optional.ofNullable(times.get(i)).orElse(0));
            }
            return timesMap;
        }
    }
}
