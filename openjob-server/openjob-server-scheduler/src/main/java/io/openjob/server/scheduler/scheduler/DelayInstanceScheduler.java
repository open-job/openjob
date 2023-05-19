package io.openjob.server.scheduler.scheduler;

import io.openjob.common.constant.LogFieldConstant;
import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.common.request.ServerDelayInstanceStopRequest;
import io.openjob.common.request.WorkerJobInstanceTaskLogFieldRequest;
import io.openjob.common.response.WorkerResponse;
import io.openjob.common.util.DateUtil;
import io.openjob.common.util.DelayUtil;
import io.openjob.common.util.FutureUtil;
import io.openjob.common.util.TaskUtil;
import io.openjob.server.common.util.ServerUtil;
import io.openjob.server.log.dao.LogDAO;
import io.openjob.server.log.dto.ProcessorLog;
import io.openjob.server.log.mapper.LogMapper;
import io.openjob.server.repository.dao.AppDAO;
import io.openjob.server.repository.dao.DelayInstanceDAO;
import io.openjob.server.repository.entity.App;
import io.openjob.server.repository.entity.Delay;
import io.openjob.server.repository.entity.DelayInstance;
import io.openjob.server.scheduler.data.DelayData;
import io.openjob.server.scheduler.dto.DelayInstanceAddRequestDTO;
import io.openjob.server.scheduler.dto.DelayInstanceAddResponseDTO;
import io.openjob.server.scheduler.dto.DelayInstanceDeleteRequestDTO;
import io.openjob.server.scheduler.dto.DelayInstanceDeleteResponseDTO;
import io.openjob.server.scheduler.dto.DelayInstancePullResponseDTO;
import io.openjob.server.scheduler.dto.DelayInstanceStatusRequestDTO;
import io.openjob.server.scheduler.dto.DelayInstanceStopRequestDTO;
import io.openjob.server.scheduler.dto.DelayInstanceStopResponseDTO;
import io.openjob.server.scheduler.dto.DelayItemPullRequestDTO;
import io.openjob.server.scheduler.dto.DelayTopicPullDTO;
import io.openjob.server.scheduler.dto.DelayTopicPullRequestDTO;
import io.openjob.server.scheduler.dto.DelayTopicPullResponseDTO;
import io.openjob.server.scheduler.dto.TopicFailCounterDTO;
import io.openjob.server.scheduler.dto.TopicReadyCounterDTO;
import io.openjob.server.scheduler.mapper.SchedulerMapper;
import io.openjob.server.scheduler.util.CacheUtil;
import io.openjob.server.scheduler.util.DelaySlotUtil;
import io.openjob.server.scheduler.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
@Component
public class DelayInstanceScheduler {
    private final DelayData delayData;
    private final DelayInstanceDAO delayInstanceDAO;
    private final AppDAO appDAO;
    private final LogDAO logDAO;

    @Autowired
    public DelayInstanceScheduler(DelayData delayData, DelayInstanceDAO delayInstanceDAO, AppDAO appDAO, LogDAO logDAO) {
        this.delayData = delayData;
        this.delayInstanceDAO = delayInstanceDAO;
        this.appDAO = appDAO;
        this.logDAO = logDAO;
    }

    /**
     * Add delay task.
     *
     * @param addRequest addRequest
     * @return DelayInstanceAddResponseDTO
     */
    public DelayInstanceAddResponseDTO add(DelayInstanceAddRequestDTO addRequest) {
        String taskId = addRequest.getTaskId();
        if (Objects.isNull(taskId)) {
            taskId = TaskUtil.getRandomTaskId();
        }

        Delay delay = this.delayData.getDelay(addRequest.getTopic());
        if (Objects.isNull(delay.getId())) {
            log.warn("Topic({}) is not exist!", addRequest.getTopic());
            throw new RuntimeException("");
        }

        addRequest.setTaskId(taskId);
        this.addDelay(addRequest);

        DelayInstanceAddResponseDTO responseDTO = new DelayInstanceAddResponseDTO();
        responseDTO.setTaskId(taskId);
        return responseDTO;
    }

    /**
     * Get topic ready count.
     *
     * @param topics topics
     * @return List
     */
    public List<TopicReadyCounterDTO> getTopicReadyCount(List<String> topics) {
        List<TopicReadyCounterDTO> counters = new ArrayList<>();
        RedisTemplate<String, Object> template = RedisUtil.getTemplate();
        topics.forEach(t -> {
            String cacheListKey = CacheUtil.getTopicListKey(t);
            TopicReadyCounterDTO counterDTO = new TopicReadyCounterDTO();
            counterDTO.setTopic(t);

            // Ready
            long count = Optional.ofNullable(template.opsForList().size(cacheListKey)).orElse(0L);
            counterDTO.setReady(count);
            counters.add(counterDTO);
        });
        return counters;
    }

    /**
     * Get topic fail count
     *
     * @param topics topics
     * @return List
     */
    public List<TopicFailCounterDTO> getTopicFailCount(List<String> topics) {
        List<TopicFailCounterDTO> counters = new ArrayList<>();
        RedisTemplate<String, Object> template = RedisUtil.getTemplate();
        topics.forEach(t -> {
            String cacheListKey = CacheUtil.getTopicListKey(DelayUtil.getFailDelayTopic(t));
            TopicFailCounterDTO counterDTO = new TopicFailCounterDTO();
            counterDTO.setTopic(t);

            // Ready
            long count = Optional.ofNullable(template.opsForList().size(cacheListKey)).orElse(0L);
            counterDTO.setCount(count);
            counters.add(counterDTO);
        });
        return counters;
    }

    /**
     * Pull topic list.
     *
     * @param pullRequestDTO pullRequest
     * @return DelayTopicPullResponseDTO
     */
    public DelayTopicPullResponseDTO pullTopicList(DelayTopicPullRequestDTO pullRequestDTO) {
        App app = this.appDAO.getAppByName(pullRequestDTO.getAppName());
        if (Objects.isNull(app)) {
            throw new RuntimeException(String.format("App name(%s) is not exist!", pullRequestDTO.getAppName()));
        }

        List<DelayTopicPullDTO> pullTopics = this.delayData.getListByAppId(app.getId()).stream()
                .map(SchedulerMapper.INSTANCE::toDelayTopicPullDTO)
                .collect(Collectors.toList());

        DelayTopicPullResponseDTO response = new DelayTopicPullResponseDTO();
        response.setTopicList(pullTopics);
        return response;
    }

    /**
     * Pull task by topic.
     *
     * @param workerAddress  workerAddress
     * @param pullRequestDTO pullRequest
     * @return list
     */
    public List<DelayInstancePullResponseDTO> pullByTopic(String workerAddress, DelayItemPullRequestDTO pullRequestDTO) {
        if (Objects.isNull(pullRequestDTO.getTopic())) {
            throw new RuntimeException("Pull topic cant not be null.");
        }

        Delay delay = this.delayData.getDelay(pullRequestDTO.getTopic());
        if (Objects.isNull(delay)) {
            throw new RuntimeException("Pull topic is not exist!");
        }

        // Pull from redis.
        String topicKey = CacheUtil.getTopicListKey(pullRequestDTO.getTopic());
        List<Object> delayList = RedisUtil.popAndRemoveFromList(topicKey, pullRequestDTO.getSize());
        List<String> taskIds = delayList.stream().map(String::valueOf).collect(Collectors.toList());

        // Set delay instance worker address.
        Map<String, String> taskIdMap = taskIds.stream().collect(Collectors.toMap(CacheUtil::getDelayDetailWorkerAddressKey, i -> workerAddress));
        RedisUtil.getTemplate().opsForValue().multiSet(taskIdMap);

        return this.delayData.getDelayInstanceList(taskIds).stream().map(di -> {
            DelayInstancePullResponseDTO responseDTO = new DelayInstancePullResponseDTO();
            responseDTO.setDelayId(delay.getId());
            responseDTO.setDelayPid(delay.getPid());
            responseDTO.setTopic(di.getTopic());
            responseDTO.setDelayParams(di.getParams());
            responseDTO.setDelayExtra(di.getExtra());
            responseDTO.setProcessorInfo(delay.getProcessorInfo());
            responseDTO.setFailRetryInterval(delay.getFailRetryInterval());
            responseDTO.setFailRetryTimes(delay.getFailRetryTimes());
            responseDTO.setExecuteTimeout(delay.getExecuteTimeout());
            responseDTO.setConcurrency(delay.getConcurrency());
            responseDTO.setBlockingSize(delay.getBlockingSize());
            responseDTO.setFailTopicEnable(delay.getFailTopicEnable());
            responseDTO.setFailTopicConcurrency(delay.getFailTopicConcurrency());
            responseDTO.setTaskId(di.getTaskId());
            return responseDTO;
        }).collect(Collectors.toList());
    }

    /**
     * Report delay status.
     *
     * @param statusList statusList
     */
    @SuppressWarnings("unchecked")
    public void report(List<DelayInstanceStatusRequestDTO> statusList) {
        // Append zset cache key.
        statusList.forEach((d) -> {
            if (d.getDelayPid() == 0) {
                d.setZsetKey(CacheUtil.getZsetKey(DelaySlotUtil.getZsetSlotId(d.getTaskId())));
                return;
            }

            d.setZsetKey(CacheUtil.getFailZsetKey(DelaySlotUtil.getFailZsetSlotId(d.getTaskId())));
        });

        // Group by zset cache key.
        Map<String, List<DelayInstanceStatusRequestDTO>> zsetKeyMap = statusList.stream()
                .collect(Collectors.groupingBy(DelayInstanceStatusRequestDTO::getZsetKey));

        List<DelayInstanceStatusRequestDTO> completeList = statusList.stream()
                .filter(d -> TaskStatusEnum.isDelayComplete(d.getStatus()))
                .collect(Collectors.toList());

        // Detail cache keys.
        List<String> completeDetailKeys = completeList.stream().map(d -> CacheUtil.getDelayDetailTaskIdKey(d.getTaskId()))
                .collect(Collectors.toList());

        // Worker address keys.
        List<String> completeAddressKeys = completeList.stream().map(d -> CacheUtil.getDelayDetailWorkerAddressKey(d.getTaskId()))
                .collect(Collectors.toList());

        // Worker address keys.
        List<String> completeRetryKeys = completeList.stream().map(d -> CacheUtil.getDelayRetryTimesKey(d.getTaskId()))
                .collect(Collectors.toList());

        // Delay status list key.
        String statusListKey = CacheUtil.getStatusListKey(DelaySlotUtil.getStatusListSlotId(UUID.randomUUID().toString()));

        // Pipeline
        RedisUtil.getTemplate().executePipelined(new SessionCallback<List<Object>>() {
            @Override
            public List<Object> execute(@Nonnull RedisOperations operations) throws DataAccessException {
                operations.multi();

                // Remove from zset
                zsetKeyMap.forEach((k, list) -> {
                    List<String> completeStatusList = list.stream().filter(d -> TaskStatusEnum.isDelayComplete(d.getStatus()))
                            .map(DelayInstanceStatusRequestDTO::getTaskId)
                            .distinct().collect(Collectors.toList());

                    if (!CollectionUtils.isEmpty(completeStatusList)) {
                        operations.opsForZSet().remove(k, completeStatusList.toArray());
                    }
                });

                // Delete detail.
                completeDetailKeys.addAll(completeAddressKeys);
                completeDetailKeys.addAll(completeRetryKeys);
                operations.delete(completeDetailKeys);

                // Push delay status to list
                operations.opsForList().rightPushAll(statusListKey, statusList.toArray());
                operations.exec();
                return null;
            }
        });
    }

    /**
     * Stop delay task.
     *
     * @param stopRequest stopRequest
     * @return DelayInstanceStopResponseDTO
     */
    public DelayInstanceStopResponseDTO stop(DelayInstanceStopRequestDTO stopRequest) {
        DelayInstanceStopResponseDTO response = new DelayInstanceStopResponseDTO();
        DelayInstance byTaskId = this.delayInstanceDAO.getByTaskId(stopRequest.getTaskId());
        if (!TaskStatusEnum.isRunning(byTaskId.getStatus())) {
            // Delete from redis
            this.removeFromCache(stopRequest.getTaskId());

            // Result
            response.setResult(1);
            return response;
        }

        String addressKey = CacheUtil.getDelayDetailWorkerAddressKey(stopRequest.getTaskId());
        Object workerAddress = RedisUtil.getTemplate().opsForValue().get(addressKey);
        if (Objects.isNull(workerAddress)) {
            // Delete from redis.
            this.removeFromCache(stopRequest.getTaskId());

            // Result
            response.setResult(2);
            return response;
        }

        try {
            ServerDelayInstanceStopRequest request = new ServerDelayInstanceStopRequest();
            request.setTaskId(stopRequest.getTaskId());
            FutureUtil.mustAsk(ServerUtil.getWorkerDelayMasterActor(String.valueOf(workerAddress)), request, WorkerResponse.class, 3000L);
            response.setResult(0);
        } catch (Throwable ex) {
            log.error("Delay stop exception!", ex);
            response.setResult(3);
        }

        // Delete from redis.
        this.removeFromCache(stopRequest.getTaskId());

        // Append processor log.
        this.appendProcessorLog(stopRequest.getTaskId(), String.valueOf(workerAddress));
        return response;
    }

    /**
     * Delete delay task.
     *
     * @param deleteRequest deleteRequest
     * @return DelayInstanceDeleteResponseDTO
     */
    public DelayInstanceDeleteResponseDTO delete(DelayInstanceDeleteRequestDTO deleteRequest) {
        // Delete from redis.
        this.removeFromCache(deleteRequest.getTaskId());
        return new DelayInstanceDeleteResponseDTO();
    }

    /**
     * Add delay task.
     *
     * @param addRequest addRequest
     */
    @SuppressWarnings("unchecked")
    private void addDelay(DelayInstanceAddRequestDTO addRequest) {
        String taskId = addRequest.getTaskId();
        String detailKey = CacheUtil.getDelayDetailTaskIdKey(taskId);
        String zsetKey = CacheUtil.getZsetKey(DelaySlotUtil.getZsetSlotId(taskId));
        String listKey = CacheUtil.getAddListKey(DelaySlotUtil.getAddListSlotId(taskId));

        RedisUtil.getTemplate().executePipelined(new SessionCallback<List<Object>>() {
            @Override
            public List<Object> execute(@Nonnull RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForValue().set(detailKey, addRequest);
                operations.opsForZSet().add(zsetKey, taskId, addRequest.getExecuteTime());
                operations.opsForList().rightPush(listKey, taskId);
                operations.exec();
                return null;
            }
        });
    }

    /**
     * Delete delay.
     *
     * @param taskId taskId
     */
    @SuppressWarnings("unchecked")
    private void removeFromCache(String taskId) {
        String detailKey = CacheUtil.getDelayDetailTaskIdKey(taskId);
        String zsetKey = CacheUtil.getZsetKey(DelaySlotUtil.getZsetSlotId(taskId));
        String listKey = CacheUtil.getAddListKey(DelaySlotUtil.getAddListSlotId(taskId));
        String addressKey = CacheUtil.getDelayDetailWorkerAddressKey(taskId);
        String retryKey = CacheUtil.getDelayRetryTimesKey(taskId);
        RedisUtil.getTemplate().executePipelined(new SessionCallback<List<Object>>() {
            @Override
            public List<Object> execute(@Nonnull RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.delete(Arrays.asList(detailKey, addressKey, retryKey));
                operations.opsForZSet().remove(zsetKey, taskId);
                operations.opsForList().remove(listKey, 1, taskId);
                operations.exec();
                return null;
            }
        });
    }

    private void appendProcessorLog(String taskId, String workerAddress) {
        try {
            Long now = DateUtil.milliLongTime();
            List<WorkerJobInstanceTaskLogFieldRequest> fields = new ArrayList<>();
            fields.add(new WorkerJobInstanceTaskLogFieldRequest(LogFieldConstant.TASK_ID, taskId));
            fields.add(new WorkerJobInstanceTaskLogFieldRequest(LogFieldConstant.LOCATION, "-"));
            fields.add(new WorkerJobInstanceTaskLogFieldRequest(LogFieldConstant.MESSAGE, "Task was terminated by user!"));
            fields.add(new WorkerJobInstanceTaskLogFieldRequest(LogFieldConstant.LEVEL, "WARN"));
            fields.add(new WorkerJobInstanceTaskLogFieldRequest(LogFieldConstant.WORKER_ADDRESS, workerAddress));
            fields.add(new WorkerJobInstanceTaskLogFieldRequest(LogFieldConstant.TIME_STAMP, now.toString()));

            ProcessorLog processorLog = new ProcessorLog();
            processorLog.setTaskId(taskId);
            processorLog.setWorkerAddress(workerAddress);
            processorLog.setTime(now);
            processorLog.setFields(LogMapper.INSTANCE.toProcessorLogFieldList(fields));

            logDAO.batchAdd(Collections.singletonList(processorLog));
        } catch (SQLException e) {
            log.error("Batch add task log failed!", e);
        }
    }
}
