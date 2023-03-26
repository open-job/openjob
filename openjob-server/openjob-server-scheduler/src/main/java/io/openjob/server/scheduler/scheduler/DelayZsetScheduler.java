package io.openjob.server.scheduler.scheduler;

import com.google.common.collect.Lists;
import io.openjob.common.SpringContext;
import io.openjob.common.constant.CommonConstant;
import io.openjob.common.util.DateUtil;
import io.openjob.server.repository.entity.Delay;
import io.openjob.server.scheduler.constant.SchedulerConstant;
import io.openjob.server.scheduler.data.DelayData;
import io.openjob.server.scheduler.dto.DelayInstanceAddRequestDTO;
import io.openjob.server.scheduler.util.CacheUtil;
import io.openjob.server.scheduler.util.DelaySlotUtil;
import io.openjob.server.scheduler.util.RedisUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.BeanCreationNotAllowedException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Log4j2
@Component
public class DelayZsetScheduler extends AbstractDelayScheduler {
    @Override
    public void start() {
        List<Long> slots = DelaySlotUtil.getCurrentZsetSlots();
        // Not slots on current node.
        if (CollectionUtils.isEmpty(slots)) {
            return;
        }

        int maxSize = slots.size();
        AtomicInteger threadId = new AtomicInteger(1);
        executorService = new ThreadPoolExecutor(
                maxSize,
                maxSize,
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(maxSize),
                r -> new Thread(r, String.format("delay-zset-%s", threadId.getAndIncrement()))
        );

        slots.forEach(s -> {
            ZsetRunnable zsetRunnable = new ZsetRunnable(s);
            runnableList.put(s, zsetRunnable);
            executorService.submit(zsetRunnable);
        });

        executorService.allowCoreThreadTimeOut(true);
        log.info("Range Delay instance slots{}", slots);
    }

    @Override
    public void stop() {
        // Not slots on current node.
        if (Objects.isNull(executorService)) {
            return;
        }

        this.executorService.shutdown();
        log.info("Range delay instance shutdown now!");
    }

    @Override
    public void refresh() {
        List<Long> slots = DelaySlotUtil.getCurrentZsetSlots();
        this.refreshSlots(slots, ZsetRunnable::new);

        log.info("Refresh range delay instance slots{}", slots);
    }

    static class ZsetRunnable extends AbstractRunnable {
        private final DelayData delayData;

        /**
         * New ZsetRunnable.
         *
         * @param currentSlotId current slot id.
         */
        public ZsetRunnable(Long currentSlotId) {
            super(currentSlotId);
            this.delayData = SpringContext.getBean(DelayData.class);
        }

        @Override
        public void run() {
            // Cache key.
            String key = CacheUtil.getZsetKey(this.currentSlotId);
            log.info("Range delay instance begin！slotId={}", this.currentSlotId);

            while (!finish.get()) {
                try {
                    this.rangeDelayInstance(key);
                } catch (InterruptedException interruptedException) {
                    log.info("Range delay instance interrupted complete！slotId={}", this.currentSlotId);
                } catch (BeanCreationNotAllowedException beanCreationNotAllowedException) {
                    // Fixed executor shutdown error.
                    log.info("Range delay instance destroy complete！slotId={}", this.currentSlotId);
                    break;
                } catch (Throwable ex) {
                    log.error("Range delay instance failed!", ex);
                }
            }

            log.info("Range delay instance complete！slotId={}", this.currentSlotId);
        }

        /**
         * Range delay instance.
         *
         * @param key zset cache key.
         * @throws InterruptedException interruptedException
         */
        private void rangeDelayInstance(String key) throws InterruptedException {
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
        private void pushAndRemoveDelayInstance(String key, Set<Object> rangeObjects) {
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

            // Execute by pipelined.
            RedisUtil.getTemplate().executePipelined(new SessionCallback<List<Object>>() {
                @Override
                public List<Object> execute(@Nonnull RedisOperations operations) throws DataAccessException {
                    operations.multi();

                    // Push by topic
                    detailListMap.forEach((t, list) -> {
                        Delay topicDelay = delayMap.get(t).get(0);
                        // Find can be push task
                        List<DelayInstanceAddRequestDTO> push2FailTopic = Lists.newArrayList();
                        List<DelayInstanceAddRequestDTO> pushTask = list.stream().filter(i -> {
                            Integer currentTimes = timesMap.get(i.getTaskId());

                            // Enable fail topic
                            if (CommonConstant.YES.equals(topicDelay.getFailTopicEnable())) {
                                // Delay task and arrive retry times.
                                if (topicDelay.getPid().equals(0L) && currentTimes >= topicDelay.getFailRetryTimes()) {
                                    push2FailTopic.add(i);
                                    return false;
                                }
                            } else {
                                if (topicDelay.getPid().equals(0L) && currentTimes >= topicDelay.getFailRetryTimes()) {
                                    // Remove task

                                    log.warn("Task has arrived retry times! taskId={}", i.getTaskId());
                                    return false;
                                }
                            }
                            return true;
                        }).collect(Collectors.toList());

                        // Not empty
                        // Push task to fail topic and inc fail task score
                        if (!CollectionUtils.isEmpty(push2FailTopic)) {
                            push2ListAndIncScore(operations, pushTask, topicDelay, true);
                        }

                        // Not empty
                        // Push task to topic and inc task score
                        if (!CollectionUtils.isEmpty(pushTask)) {
                            push2ListAndIncScore(operations, pushTask, topicDelay, false);
                        }
                    });

                    operations.exec();
                    return null;
                }

                private void push2ListAndIncScore(RedisOperations<String, Object> operations,
                                                  List<DelayInstanceAddRequestDTO> pushTask,
                                                  Delay topicDelay,
                                                  Boolean isFailTopic) {
                    // List cache key.
                    String cacheListKey = getListCacheKey(topicDelay, isFailTopic);

                    // Remove task id.
                    // Fixed retry task id.
                    pushTask.forEach(i -> {
                        operations.opsForList().remove(cacheListKey, 0, i.getTaskId());

                        // Retry times.
                        String delayRetryTimesKey = CacheUtil.getDelayRetryTimesKey(i.getTaskId());
                        operations.opsForValue().increment(delayRetryTimesKey);
                    });

                    if (CollectionUtils.isEmpty(pushTask)){
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
                        operations.opsForZSet().incrementScore(key, d.getTaskId(), retryTime);
                    });
                }

                private String getListCacheKey(Delay topicDelay, Boolean isFailTopic) {
                    String cacheListKey = CacheUtil.getTopicListKey(topicDelay.getTopic());
                    if (isFailTopic) {
                        Delay delay = SpringContext.getBean(DelayData.class).getDelayById(topicDelay.getCid());
                        cacheListKey = CacheUtil.getTopicListKey(delay.getTopic());
                    }
                    return cacheListKey;
                }
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
