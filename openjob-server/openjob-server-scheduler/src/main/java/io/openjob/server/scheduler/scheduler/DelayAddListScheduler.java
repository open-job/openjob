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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Component
public class DelayAddListScheduler extends AbstractDelayScheduler {
    @Override
    public void start() {
        List<Long> slots = DelaySlotUtil.getCurrentAddListSlots();
        int maxSize = slots.size() > 0 ? slots.size() : 1;

        AtomicInteger threadId = new AtomicInteger(1);
        executorService = new ThreadPoolExecutor(
                maxSize,
                maxSize,
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(maxSize),
                r -> new Thread(r, String.format("delay-add-list-%s", threadId.getAndIncrement()))
        );

        slots.forEach(s -> {
            AddListRunnable listRunnable = new AddListRunnable(s);
            runnableList.put(s, listRunnable);
            executorService.submit(listRunnable);
        });

        executorService.allowCoreThreadTimeOut(true);
        log.info("Add List Delay instance slots{}", slots);
    }

    @Override
    public void stop() {
        this.executorService.shutdownNow();
        log.info("Add List delay instance shutdown now!");
    }

    @Override
    public void refresh() {
        List<Long> slots = DelaySlotUtil.getCurrentAddListSlots();
        this.refreshSlots(slots, AddListRunnable::new);

        log.info("Refresh add list delay instance slots{}", slots);
    }

    static class AddListRunnable extends AbstractRunnable {
        protected final DelayInstanceDAO delayInstanceDAO;
        protected final DelayData delayData;

        public AddListRunnable(Long currentSlotId) {
            super(currentSlotId);
            this.delayData = SpringContext.getBean(DelayData.class);
            this.delayInstanceDAO = SpringContext.getBean(DelayInstanceDAO.class);
        }

        @Override
        public void run() {
            // Cache key.
            String key = CacheUtil.getAddListKey(this.currentSlotId);
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

            // Get delay instance detail list
            List<String> taskIds = popObjects.stream().map(String::valueOf).collect(Collectors.toList());
            List<DelayInstanceAddRequestDTO> detailList = this.delayData.getDelayInstanceList(taskIds);
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
                delayInstance.setStatus(1);
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
