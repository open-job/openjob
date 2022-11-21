package io.openjob.server.scheduler.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.openjob.common.util.DateUtil;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.scheduler.dto.DelayInstanceAddRequestDTO;
import io.openjob.server.scheduler.util.CacheUtil;
import io.openjob.server.scheduler.util.RedisUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Log4j2
@Service
public class DelaySchedulingService {
    private ThreadPoolExecutor executorService;
    private final Map<Long, ZsetRunnable> runnableList = Maps.newConcurrentMap();

    public void start() {
        List<Long> slots = this.getCurrentZsetSlots();
        int maxSize = slots.size() > 0 ? slots.size() : 1;

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
    }

    static class ZsetRunnable implements Runnable {
        private final Long currentSlotId;
        private final AtomicBoolean finish = new AtomicBoolean(false);

        public ZsetRunnable(Long currentSlotId) {
            this.currentSlotId = currentSlotId;
        }

        @Override
        public void run() {
            // Cache key.
            String key = CacheUtil.getZsetKey(this.currentSlotId);

            while (!finish.get()) {
                try {
                    this.rangeDelayInstance(key);
                } catch (Throwable ex) {
                    log.error("Range delay instance failed!", ex);
                }
            }
        }

        /**
         * Set finish.
         *
         * @param finish finish
         */
        public void setFinish(Boolean finish) {
            this.finish.set(finish);
        }

        private void rangeDelayInstance(String key) throws InterruptedException {
            Set<Object> rangeObjects = RedisUtil.getTemplate().opsForZSet().rangeByScore(key, DateUtil.timestamp(), -1, 0, 50);
            if (Objects.isNull(rangeObjects)) {
                return;
            }

            this.pushAndRemoveDelayInstance(key, rangeObjects);

            if (rangeObjects.isEmpty()) {
                Thread.sleep(500);
            }
        }

        @SuppressWarnings("unchecked")
        private void pushAndRemoveDelayInstance(String key, Set<Object> rangeObjects) {
            List<String> removeMembers = Lists.newArrayList();
            for (Object rangeObject : rangeObjects) {
                ZSetOperations.TypedTuple<Object> typedTuple = (ZSetOperations.TypedTuple<Object>) rangeObject;
                Object value = typedTuple.getValue();
                removeMembers.add(String.valueOf(value));
            }

            List<String> cacheKeys = Lists.newArrayList();
            removeMembers.forEach(rm -> {
                cacheKeys.add(CacheUtil.getDetailKey(rm));
            });

            List<Object> cacheList = RedisUtil.getTemplate().opsForValue().multiGet(cacheKeys);
            if (CollectionUtils.isEmpty(cacheList)) {
                return;
            }

            List<DelayInstanceAddRequestDTO> detailList = Lists.newArrayList();
            for (Object detail : cacheList) {
                if (Objects.isNull(detail)) {
                    continue;
                }

                if (detail instanceof DelayInstanceAddRequestDTO) {
                    detailList.add((DelayInstanceAddRequestDTO) detail);
                }
            }

            Map<String, List<DelayInstanceAddRequestDTO>> detailListMap = detailList.stream()
                    .collect(Collectors.groupingBy(DelayInstanceAddRequestDTO::getTopic));

            RedisUtil.getTemplate().executePipelined(new SessionCallback<List<Object>>() {
                @Override
                public List<Object> execute(@Nonnull RedisOperations operations) throws DataAccessException {
                    operations.multi();

                    detailListMap.forEach((t, list) -> {
                        String cacheListKey = CacheUtil.getTopicKey(t);
                        operations.opsForList().rightPushAll(cacheListKey, list.toArray());
                    });

                    RedisUtil.getTemplate().opsForZSet().remove(key, removeMembers.toArray());
                    return operations.exec();
                }
            });
        }
    }

    private List<Long> getCurrentZsetSlots() {
        List<Long> slots = Lists.newArrayList();
        ClusterContext.getCurrentSlots().forEach(i -> {
            if (i <= ClusterContext.getSystem().getDelayZsetMaxSlot()) {
                slots.add(i);
            }
        });
        return slots;
    }
}
