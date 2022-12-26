package io.openjob.server.scheduler.scheduler;

import io.openjob.common.util.DateUtil;
import io.openjob.common.util.TaskUtil;
import io.openjob.server.repository.dao.AppDAO;
import io.openjob.server.repository.dao.DelayInstanceDAO;
import io.openjob.server.repository.entity.App;
import io.openjob.server.repository.entity.Delay;
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
import io.openjob.server.scheduler.mapper.SchedulerMapper;
import io.openjob.server.scheduler.util.CacheUtil;
import io.openjob.server.scheduler.util.DelaySlotUtil;
import io.openjob.server.scheduler.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
@Component
public class DelayInstanceScheduler {
    private final DelayData delayData;
    private final DelayInstanceDAO delayInstanceDAO;
    private final AppDAO appDAO;

    @Autowired
    public DelayInstanceScheduler(DelayData delayData, DelayInstanceDAO delayInstanceDAO, AppDAO appDAO) {
        this.delayData = delayData;
        this.delayInstanceDAO = delayInstanceDAO;
        this.appDAO = appDAO;
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
            taskId = TaskUtil.getRandomUniqueId();
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
     * Stop delay task.
     *
     * @param stopRequest stopRequest
     * @return DelayInstanceStopResponseDTO
     */
    public DelayInstanceStopResponseDTO stop(DelayInstanceStopRequestDTO stopRequest) {
        System.out.println(stopRequest);
        return new DelayInstanceStopResponseDTO();
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
     * @param pullRequestDTO pullRequest
     * @return list
     */
    public List<DelayInstancePullResponseDTO> pullByTopic(DelayItemPullRequestDTO pullRequestDTO) {
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

        return this.delayData.getDelayInstanceList(taskIds).stream().map(di -> {
            DelayInstancePullResponseDTO responseDTO = new DelayInstancePullResponseDTO();
            responseDTO.setDelayId(delay.getId());
            responseDTO.setTopic(di.getTopic());
            responseDTO.setDelayParams(di.getParams());
            responseDTO.setDelayExtra(di.getExtra());
            responseDTO.setProcessorInfo(delay.getProcessorInfo());
            responseDTO.setFailRetryInterval(delay.getFailRetryInterval());
            responseDTO.setFailRetryTimes(delay.getFailRetryTimes());
            responseDTO.setExecuteTimeout(delay.getExecuteTimeout());
            responseDTO.setConcurrency(delay.getConcurrency());
            responseDTO.setBlockingSize(delay.getBlockingSize());
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
        statusList.forEach((d) -> d.setZsetKey(CacheUtil.getZsetKey(DelaySlotUtil.getZsetSlotId(d.getTaskId()))));

        // Group by zset cache key.
        Map<String, List<DelayInstanceStatusRequestDTO>> zsetKeyMap = statusList.stream()
                .collect(Collectors.groupingBy(DelayInstanceStatusRequestDTO::getZsetKey));

        // Detail cache keys.
        List<String> detailKeys = statusList.stream().map(d -> CacheUtil.getDelayDetailTaskIdKey(d.getTaskId()))
                .collect(Collectors.toList());

        // Delay status list key.
        String statusListKey = CacheUtil.getStatusListKey(DelaySlotUtil.getStatusListSlotId(UUID.randomUUID().toString()));

        // Pipeline
        RedisUtil.getTemplate().executePipelined(new SessionCallback<List<Object>>() {
            @Override
            public List<Object> execute(@Nonnull RedisOperations operations) throws DataAccessException {
                operations.multi();

                // Remove from zset
                zsetKeyMap.forEach((k, list) -> operations.opsForZSet().remove(
                        k,
                        list.stream().map(DelayInstanceStatusRequestDTO::getTaskId).distinct().toArray()
                ));

                // Delete detail.
                operations.delete(detailKeys);

                // Push delay status to list
                operations.opsForList().rightPushAll(statusListKey, statusList.toArray());
                operations.exec();
                return null;
            }
        });
    }

    /**
     * Delete delay task.
     *
     * @param deleteRequest deleteRequest
     * @return DelayInstanceDeleteResponseDTO
     */
    public DelayInstanceDeleteResponseDTO delete(DelayInstanceDeleteRequestDTO deleteRequest) {
        // Delete from redis.
        this.deleteDelay(deleteRequest);
        return new DelayInstanceDeleteResponseDTO();
    }

    /**
     * Add delay task.
     *
     * @param addRequest addRequest
     */
    @SuppressWarnings("unchecked")
    private void addDelay(DelayInstanceAddRequestDTO addRequest) {
        if (addRequest.getExecuteTime() < DateUtil.timestamp()) {
            throw new RuntimeException("Execute time is expire!");
        }

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
     * @param deleteRequest deleteRequest
     */
    @SuppressWarnings("unchecked")
    private void deleteDelay(DelayInstanceDeleteRequestDTO deleteRequest) {
        String taskId = deleteRequest.getTaskId();
        String detailKey = CacheUtil.getDelayDetailTaskIdKey(taskId);
        String zsetKey = CacheUtil.getZsetKey(DelaySlotUtil.getZsetSlotId(taskId));
        String listKey = CacheUtil.getAddListKey(DelaySlotUtil.getAddListSlotId(taskId));
        RedisUtil.getTemplate().executePipelined(new SessionCallback<List<Object>>() {
            @Override
            public List<Object> execute(@Nonnull RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.delete(detailKey);
                operations.opsForZSet().remove(zsetKey, taskId);
                operations.opsForList().remove(listKey, 1, taskId);
                return operations.exec();
            }
        });
    }
}
