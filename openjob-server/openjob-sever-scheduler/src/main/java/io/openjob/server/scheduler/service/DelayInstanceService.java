package io.openjob.server.scheduler.service;

import io.openjob.common.constant.InstanceStatusEnum;
import io.openjob.common.request.WorkerDelayAddRequest;
import io.openjob.common.request.WorkerDelayPullItemRequest;
import io.openjob.common.request.WorkerDelayPullRequest;
import io.openjob.common.response.ServerDelayAddResponse;
import io.openjob.common.response.ServerDelayInstanceResponse;
import io.openjob.common.response.ServerDelayPullResponse;
import io.openjob.common.util.DateUtil;
import io.openjob.common.util.TaskUtil;
import io.openjob.server.common.util.SlotsUtil;
import io.openjob.server.repository.dao.DelayInstanceDAO;
import io.openjob.server.repository.entity.Delay;
import io.openjob.server.repository.entity.DelayInstance;
import io.openjob.server.scheduler.data.DelayData;
import io.openjob.server.scheduler.dto.DelayDTO;
import io.openjob.server.scheduler.util.CacheUtil;
import io.openjob.server.scheduler.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
@Service
public class DelayInstanceService {
    private final DelayInstanceDAO delayInstanceDAO;
    private final DelayData delayData;

    @Autowired
    public DelayInstanceService(DelayInstanceDAO delayInstanceDAO, DelayData delayData) {
        this.delayInstanceDAO = delayInstanceDAO;
        this.delayData = delayData;
    }

    public ServerDelayPullResponse pullInstance(WorkerDelayPullRequest pullRequest) {
        List<ServerDelayInstanceResponse> responses = new ArrayList<>();

        // Pull delay instance.
        pullRequest.getPullItems().forEach(item -> responses.addAll(this.pullByTopic(item)));

        ServerDelayPullResponse delayPullResponse = new ServerDelayPullResponse();
        delayPullResponse.setDelayInstanceResponses(responses);
        return delayPullResponse;
    }

    public ServerDelayAddResponse addDelayInstance(WorkerDelayAddRequest addRequest) {
        String taskId = addRequest.getTaskId();
        if (Objects.isNull(taskId)) {
            taskId = TaskUtil.getRandomUniqueId();
        }

        ServerDelayAddResponse serverDelayAddResponse = new ServerDelayAddResponse(addRequest.getDeliveryId());
        Delay delay = this.delayData.getDelay(addRequest.getNamespaceId(), addRequest.getTopic());
        if (Objects.isNull(delay.getId())) {
            log.warn("Topic({}) is not exist!", addRequest.getTopic());
            return serverDelayAddResponse;
        }

        int now = DateUtil.now();
        DelayInstance delayInstance = new DelayInstance();
        delayInstance.setNamespaceId(addRequest.getNamespaceId());
        delayInstance.setAppId(delay.getAppId());
        delayInstance.setTaskId(taskId);
        delayInstance.setTopic(addRequest.getTopic());
        delayInstance.setDelayId(delay.getId());
        delayInstance.setDelayParams(addRequest.getParams());
        delayInstance.setDelayExtra(addRequest.getExtra());
        delayInstance.setExecuteTime(addRequest.getExecuteTime());
        delayInstance.setCreateTime(now);
        delayInstance.setUpdateTime(now);
        delayInstance.setStatus(InstanceStatusEnum.WAITING.getStatus());
        delayInstance.setSlotsId(SlotsUtil.getSlotsId(taskId));
        this.delayInstanceDAO.save(delayInstance);

        serverDelayAddResponse.setTaskId(taskId);
        return serverDelayAddResponse;
    }

    private List<ServerDelayInstanceResponse> pullByTopic(WorkerDelayPullItemRequest item) {
        List<ServerDelayInstanceResponse> responses = new ArrayList<>();

        // Pull from redis.
        String topicKey = CacheUtil.getTopicKey(item.getTopic());
        List<Object> delayList = RedisUtil.getTemplate().opsForList().leftPop(topicKey, item.getSize());

        // Not empty
        if (Objects.nonNull(delayList)) {
            delayList.forEach(d -> {
                if (d instanceof DelayDTO) {
                    DelayDTO delayDTO = (DelayDTO) d;
                    ServerDelayInstanceResponse delayInstanceResponse = new ServerDelayInstanceResponse();
                    delayInstanceResponse.setDelayId(delayDTO.getDelayId());
                    delayInstanceResponse.setDelayParams(delayDTO.getDelayParams());
                    delayInstanceResponse.setDelayExtra(delayDTO.getDelayExtra());
                    delayInstanceResponse.setTopic(delayDTO.getTopic());
                    delayInstanceResponse.setProcessorInfo(delayDTO.getProcessorInfo());
                    delayInstanceResponse.setFailRetryTimes(delayDTO.getFailRetryTimes());
                    delayInstanceResponse.setFailRetryInterval(delayDTO.getFailRetryInterval());
                    delayInstanceResponse.setExecuteTimeout(delayDTO.getExecuteTimeout());
                    delayInstanceResponse.setBlockingSize(delayDTO.getBlockingSize());
                    delayInstanceResponse.setConcurrency(delayDTO.getConcurrency());
                    responses.add(delayInstanceResponse);
                }
            });
        }
        return responses;
    }
}
