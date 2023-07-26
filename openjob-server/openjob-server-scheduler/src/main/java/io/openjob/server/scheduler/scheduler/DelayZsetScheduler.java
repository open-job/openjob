package io.openjob.server.scheduler.scheduler;

import io.openjob.server.alarm.AlarmEvent;
import io.openjob.server.alarm.constant.AlarmEventEnum;
import io.openjob.server.alarm.dto.AlarmEventDTO;
import io.openjob.common.constant.LogFieldConstant;
import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.common.request.WorkerJobInstanceTaskLogFieldRequest;
import io.openjob.common.util.DateUtil;
import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.log.dto.ProcessorLogDTO;
import io.openjob.server.log.dto.ProcessorLogFieldDTO;
import io.openjob.server.scheduler.constant.SchedulerConstant;
import io.openjob.server.scheduler.dto.DelayInstanceAddRequestDTO;
import io.openjob.server.scheduler.dto.DelayInstanceStatusRequestDTO;
import io.openjob.server.scheduler.util.CacheUtil;
import io.openjob.server.scheduler.util.DelaySlotUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.BeanCreationNotAllowedException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Log4j2
@Component
public class DelayZsetScheduler extends AbstractDelayZsetScheduler {
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

    static class ZsetRunnable extends AbstractZsetRunnable {

        /**
         * New ZsetRunnable.
         *
         * @param currentSlotId current slot id.
         */
        public ZsetRunnable(Long currentSlotId) {
            super(currentSlotId);
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

        @Override
        protected void push2FailZset(RedisOperations<String, Object> operations, String originZsetKey, String topic, List<DelayInstanceAddRequestDTO> list) {
            String currentTopicKey = CacheUtil.getTopicListKey(topic);
            long timestamp = DateUtil.timestamp() + SchedulerConstant.DELAY_RETRY_AFTER;

            list.forEach(d -> {
                String taskId = d.getTaskId();
                String zsetKey = CacheUtil.getFailZsetKey(DelaySlotUtil.getZsetSlotId(taskId));

                // Remove topic list key.
                operations.opsForList().remove(currentTopicKey, 0, d.getTaskId());

                // Add to fail zset
                operations.opsForZSet().add(zsetKey, taskId, timestamp);
            });

            // Remove normal zset
            List<String> taskIds = list.stream().map(DelayInstanceAddRequestDTO::getTaskId).collect(Collectors.toList());
            operations.opsForZSet().remove(originZsetKey, taskIds.toArray());

            log.warn("Push task to fail zset taskIds={}", taskIds);
        }

        @Override
        protected void ignoreTaskList(RedisOperations<String, Object> operations, String zsetKey, List<DelayInstanceAddRequestDTO> list) {
            list.forEach(d -> {
                // Cache keys
                String taskId = d.getTaskId();
                String detailKey = CacheUtil.getDelayDetailTaskIdKey(taskId);
                String listKey = CacheUtil.getAddListKey(DelaySlotUtil.getAddListSlotId(taskId));
                String addressKey = CacheUtil.getDelayDetailWorkerAddressKey(taskId);
                String retryKey = CacheUtil.getDelayRetryTimesKey(taskId);

                // Remove and delete keys
                operations.delete(Arrays.asList(detailKey, addressKey, retryKey));
                operations.opsForList().remove(listKey, 1, taskId);
            });

            List<String> taskIds = list.stream().map(DelayInstanceAddRequestDTO::getTaskId).collect(Collectors.toList());
            operations.opsForZSet().remove(zsetKey, taskIds.toArray());

            log.warn("Ignore taskIds={}", taskIds);

            // Update task status
            String statusListKey = CacheUtil.getStatusListKey(DelaySlotUtil.getStatusListSlotId(UUID.randomUUID().toString()));
            Stream<DelayInstanceStatusRequestDTO> statusList = list.stream().map(d -> {
                DelayInstanceStatusRequestDTO delayInstanceStatusRequestDTO = new DelayInstanceStatusRequestDTO();
                delayInstanceStatusRequestDTO.setTaskId(d.getTaskId());
                delayInstanceStatusRequestDTO.setStatus(TaskStatusEnum.FAILED.getStatus());
                delayInstanceStatusRequestDTO.setWorkerAddress("");
                delayInstanceStatusRequestDTO.setCompleteTime(DateUtil.timestamp());
                return delayInstanceStatusRequestDTO;
            });
            operations.opsForList().rightPushAll(statusListKey, statusList.toArray());

            // Append processor log.
            this.appendProcessorLog(taskIds);

            // Add alarm event.
            this.addAlarmEvent(list);
        }

        @Override
        protected String getCacheKey(String topic) {
            return CacheUtil.getTopicListKey(topic);
        }

        /**
         * Add alarm event
         *
         * @param list list
         */
        private void addAlarmEvent(List<DelayInstanceAddRequestDTO> list) {
            list.forEach(d -> {
                AlarmEventDTO alarmEventDTO = new AlarmEventDTO();
                alarmEventDTO.setJobUniqueId(d.getTopic());
                alarmEventDTO.setInstanceId(d.getTaskId());
                alarmEventDTO.setName(AlarmEventEnum.DELAY_TASK_IGNORE.getEvent());
                alarmEventDTO.setMessage("Delay task have reached the retry times and has been discarded!");
                AlarmEvent.add(alarmEventDTO);
            });
        }

        /**
         * Append processor log.
         *
         * @param taskIds taskIds
         */
        private void appendProcessorLog(List<String> taskIds) {
            try {
                List<ProcessorLogDTO> processorLogs = taskIds.stream().map(tid -> {
                    Long now = DateUtil.milliLongTime();
                    List<WorkerJobInstanceTaskLogFieldRequest> fields = new ArrayList<>();
                    fields.add(new WorkerJobInstanceTaskLogFieldRequest(LogFieldConstant.TASK_ID, tid));
                    fields.add(new WorkerJobInstanceTaskLogFieldRequest(LogFieldConstant.LOCATION, "-"));
                    fields.add(new WorkerJobInstanceTaskLogFieldRequest(LogFieldConstant.MESSAGE, "Delay task have reached the retry times. will be to discarded!"));
                    fields.add(new WorkerJobInstanceTaskLogFieldRequest(LogFieldConstant.LEVEL, "WARN"));
                    fields.add(new WorkerJobInstanceTaskLogFieldRequest(LogFieldConstant.WORKER_ADDRESS, ""));
                    fields.add(new WorkerJobInstanceTaskLogFieldRequest(LogFieldConstant.TIME_STAMP, now.toString()));

                    ProcessorLogDTO processorLog = new ProcessorLogDTO();
                    processorLog.setTaskId(tid);
                    processorLog.setWorkerAddress("");
                    processorLog.setTime(now);
                    processorLog.setFields(BeanMapperUtil.mapList(fields, WorkerJobInstanceTaskLogFieldRequest.class, ProcessorLogFieldDTO.class));
                    return processorLog;
                }).collect(Collectors.toList());

                logDAO.batchAdd(processorLogs);
            } catch (Exception e) {
                log.error("Batch add task ignore log failed!", e);
            }
        }
    }
}
