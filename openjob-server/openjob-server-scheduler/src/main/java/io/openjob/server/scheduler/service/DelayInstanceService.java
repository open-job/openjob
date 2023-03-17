package io.openjob.server.scheduler.service;

import io.openjob.common.request.WorkerDelayAddRequest;
import io.openjob.common.request.WorkerDelayItemPullRequest;
import io.openjob.common.request.WorkerDelayPullRequest;
import io.openjob.common.request.WorkerDelayStatusRequest;
import io.openjob.common.request.WorkerDelayTopicPullRequest;
import io.openjob.common.response.ServerDelayAddResponse;
import io.openjob.common.response.ServerDelayInstanceResponse;
import io.openjob.common.response.ServerDelayPullResponse;
import io.openjob.common.response.ServerDelayTopicPullResponse;
import io.openjob.server.scheduler.dto.DelayDTO;
import io.openjob.server.scheduler.dto.DelayInstanceAddRequestDTO;
import io.openjob.server.scheduler.dto.DelayInstanceAddResponseDTO;
import io.openjob.server.scheduler.dto.DelayInstancePullResponseDTO;
import io.openjob.server.scheduler.dto.DelayInstanceStatusRequestDTO;
import io.openjob.server.scheduler.dto.DelayItemPullRequestDTO;
import io.openjob.server.scheduler.dto.DelayTopicPullRequestDTO;
import io.openjob.server.scheduler.dto.DelayTopicPullResponseDTO;
import io.openjob.server.scheduler.mapper.SchedulerMapper;
import io.openjob.server.scheduler.scheduler.DelayInstanceScheduler;
import io.openjob.server.scheduler.util.CacheUtil;
import io.openjob.server.scheduler.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
@Service
public class DelayInstanceService {
    private final DelayInstanceScheduler delayInstanceScheduler;

    @Autowired
    public DelayInstanceService(DelayInstanceScheduler delayInstanceScheduler) {
        this.delayInstanceScheduler = delayInstanceScheduler;
    }

    /**
     * Pull instance.
     *
     * @param pullRequest pull request.
     * @return ServerDelayPullResponse
     */
    public ServerDelayPullResponse pullInstance(WorkerDelayPullRequest pullRequest) {
        List<ServerDelayInstanceResponse> responses = new ArrayList<>();

        // Pull delay instance.
        pullRequest.getPullItems().forEach(item -> {
            DelayItemPullRequestDTO delayItemPullRequestDTO = SchedulerMapper.INSTANCE.toDelayItemPullRequestDTO(item);
            List<DelayInstancePullResponseDTO> responseList = this.delayInstanceScheduler.pullByTopic(pullRequest.getWorkerAddress(), delayItemPullRequestDTO);
            responses.addAll(SchedulerMapper.INSTANCE.toServerDelayPullResponseList(responseList));
        });

        ServerDelayPullResponse delayPullResponse = new ServerDelayPullResponse();
        delayPullResponse.setDelayInstanceResponses(responses);
        return delayPullResponse;
    }

    /**
     * Add delay instance.
     *
     * @param addRequest add request.
     * @return ServerDelayAddResponse
     */
    public ServerDelayAddResponse addDelayInstance(WorkerDelayAddRequest addRequest) {
        ServerDelayAddResponse serverDelayAddResponse = new ServerDelayAddResponse(addRequest.getDeliveryId());
        DelayInstanceAddRequestDTO addRequestDTO = SchedulerMapper.INSTANCE.toDelayInstanceAddRequestDTO(addRequest);
        DelayInstanceAddResponseDTO addResponseDTO = this.delayInstanceScheduler.add(addRequestDTO);
        serverDelayAddResponse.setTaskId(addResponseDTO.getTaskId());
        return serverDelayAddResponse;
    }

    /**
     * Pull topic list.
     *
     * @param pullRequest pullRequest
     * @return ServerDelayTopicPullResponse
     */
    public ServerDelayTopicPullResponse pullTopicList(WorkerDelayTopicPullRequest pullRequest) {
        DelayTopicPullRequestDTO delayTopicPullRequestDTO = SchedulerMapper.INSTANCE.toDelayTopicPullRequestDTO(pullRequest);
        DelayTopicPullResponseDTO topicListDTO = this.delayInstanceScheduler.pullTopicList(delayTopicPullRequestDTO);
        ServerDelayTopicPullResponse response = new ServerDelayTopicPullResponse();
        response.setTopicList(SchedulerMapper.INSTANCE.toServerDelayTopicResponseList(topicListDTO.getTopicList()));
        return response;
    }

    /**
     * Handle delay status.
     *
     * @param workerDelayStatusRequest workerDelayStatusRequest
     */
    public void handleDelayStatus(WorkerDelayStatusRequest workerDelayStatusRequest) {
        List<DelayInstanceStatusRequestDTO> statusList = SchedulerMapper.INSTANCE.toDelayInstanceStatusList(workerDelayStatusRequest.getTaskList());
        this.delayInstanceScheduler.report(statusList);
    }
}
