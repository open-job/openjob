package io.openjob.server.scheduler.scheduler;

import com.google.common.collect.Lists;
import io.openjob.common.SpringContext;
import io.openjob.common.constant.CommonConstant;
import io.openjob.common.util.DateUtil;
import io.openjob.server.repository.dao.DelayInstanceDAO;
import io.openjob.server.repository.entity.Delay;
import io.openjob.server.repository.entity.DelayInstance;
import io.openjob.server.scheduler.data.DelayData;
import io.openjob.server.scheduler.dto.DelayInstanceAddRequestDTO;
import io.openjob.server.scheduler.util.CacheUtil;
import io.openjob.server.scheduler.util.DelaySlotUtil;
import io.openjob.server.scheduler.util.RedisUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.BeanCreationNotAllowedException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public class DelayListScheduler extends AbstractDelayScheduler {

    @Override
    public void start() {
        List<Long> slots = DelaySlotUtil.getCurrentListSlots();
        int maxSize = slots.size() > 0 ? slots.size() : 1;

        AtomicInteger threadId = new AtomicInteger(1);
        executorService = new ThreadPoolExecutor(
                maxSize,
                maxSize,
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(maxSize),
                r -> new Thread(r, String.format("delay-list-%s", threadId.getAndIncrement()))
        );

        slots.forEach(s -> {
            ListRunnable listRunnable = new ListRunnable(s);
            runnableList.put(s, listRunnable);
            executorService.submit(listRunnable);
        });

        executorService.allowCoreThreadTimeOut(true);
        log.info("List Delay instance slots{}", slots);
    }

    @Override
    public void stop() {
        this.executorService.shutdownNow();
        log.info("List delay instance shutdown now!");
    }

    @Override
    public void refresh() {
        List<Long> slots = DelaySlotUtil.getCurrentListSlots();
        this.refreshSlots(slots, ListRunnable::new);

        log.info("Refresh list delay instance slots{}", slots);
    }

    static class ListRunnable extends AbstractRunnable {
        protected final DelayInstanceDAO delayInstanceDAO;
        protected final DelayData delayData;

        public ListRunnable(Long currentSlotId) {
            super(currentSlotId);
            this.delayData = SpringContext.getBean(DelayData.class);
            this.delayInstanceDAO = SpringContext.getBean(DelayInstanceDAO.class);
        }

        @Override
        public void run() {
            // Cache key.
            String key = CacheUtil.getListKey(this.currentSlotId);
            log.info("List delay instance begin！slotId={}", this.currentSlotId);

            while (!finish.get()) {
                try {
                    this.batchSaveFromList(key);
                } catch (InterruptedException interruptedException) {
                    log.info("List delay instance interrupted complete！slotId={}", this.currentSlotId);
                } catch (BeanCreationNotAllowedException beanCreationNotAllowedException) {
                    // Fixed executor shutdown error.
                    log.info("List delay instance destroy complete！slotId={}", this.currentSlotId);
                    break;
                } catch (Throwable ex) {
                    log.error("List delay instance failed!", ex);
                }
            }

            log.info("List delay instance complete！slotId={}", this.currentSlotId);
        }

        public void batchSaveFromList(String key) throws InterruptedException {
            List<Object> popObjects = RedisUtil.popAndRemoveFromList(key, 100);

            // Delay instance is empty.
            if (CollectionUtils.isEmpty(popObjects)) {
                Thread.sleep(500);
                return;
            }

            List<String> cacheKeys = Lists.newArrayList();
            popObjects.forEach(o -> {
                if (o instanceof String) {
                    String taskId = (String) o;
                    cacheKeys.add(CacheUtil.getDetailKey(taskId));
                }
            });

            // Get delay instance detail list
            List<DelayInstanceAddRequestDTO> detailList = this.getDelayInstanceList(cacheKeys);
            if (CollectionUtils.isEmpty(detailList)) {
                return;
            }

            Set<String> topics = detailList.stream()
                    .collect(Collectors.groupingBy(DelayInstanceAddRequestDTO::getTopic))
                    .keySet();

            Map<String, List<Delay>> delayMap = this.delayData.getDelayList(new ArrayList<>(topics)).stream()
                    .collect(Collectors.groupingBy(Delay::getTopic));

            Long timestamp = DateUtil.timestamp();
            List<DelayInstance> delayInstanceList = Lists.newArrayList();
            detailList.forEach(d -> {
                Optional<List<Delay>> delays = Optional.ofNullable(delayMap.get(d.getTopic()));
                if (!delays.isPresent()) {
                    return;
                }

                Delay topicDelay = delays.get().get(0);
                DelayInstance delayInstance = new DelayInstance();
                delayInstance.setNamespaceId(topicDelay.getNamespaceId());
                delayInstance.setAppId(topicDelay.getAppId());
                delayInstance.setDelayId(topicDelay.getId());
                delayInstance.setTaskId(d.getTaskId());
                delayInstance.setTopic(d.getTopic());
                delayInstance.setDelayParams(d.getParams());
                delayInstance.setDelayExtra(d.getExtra());
                delayInstance.setDeleted(CommonConstant.NO);
                delayInstance.setDeleteTime(0L);
                delayInstance.setExecuteTime(d.getExecuteTime());
                delayInstance.setCreateTime(timestamp);
                delayInstance.setUpdateTime(timestamp);
                delayInstanceList.add(delayInstance);
            });
            this.delayInstanceDAO.batchSave(delayInstanceList);
        }
    }
}
