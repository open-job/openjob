package io.openjob.server.scheduler.mapper;

import io.openjob.common.request.WorkerDelayItemPullRequest;
import io.openjob.common.request.WorkerDelayTopicPullRequest;
import io.openjob.common.response.ServerDelayInstanceResponse;
import io.openjob.common.response.ServerDelayTopicResponse;
import io.openjob.server.repository.entity.Delay;
import io.openjob.server.scheduler.dto.DelayInstancePullResponseDTO;
import io.openjob.server.scheduler.dto.DelayItemPullRequestDTO;
import io.openjob.server.scheduler.dto.DelayTopicPullDTO;
import io.openjob.server.scheduler.dto.DelayTopicPullRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Mapper
public interface SchedulerMapper {
    SchedulerMapper INSTANCE = Mappers.getMapper(SchedulerMapper.class);

    /**
     * To DelayTopicPullDTO
     *
     * @param delay delay
     * @return DelayTopicPullDTO
     */
    DelayTopicPullDTO toDelayTopicPullDTO(Delay delay);

    /**
     * To DelayTopicPullRequestDTO
     *
     * @param request request
     * @return DelayTopicPullRequestDTO
     */
    DelayTopicPullRequestDTO toDelayTopicPullRequestDTO(WorkerDelayTopicPullRequest request);

    /**
     * To ServerDelayTopicResponse list.
     *
     * @param pullList pullList
     * @return List
     */
    List<ServerDelayTopicResponse> toServerDelayTopicResponseList(List<DelayTopicPullDTO> pullList);

    /**
     * To DelayItemPullRequestDTO
     *
     * @param pullRequest pullRequest
     * @return DelayItemPullRequestDTO
     */
    DelayItemPullRequestDTO toDelayItemPullRequestDTO(WorkerDelayItemPullRequest pullRequest);

    /**
     * To ServerDelayPullResponse list.
     *
     * @param pullList pullList
     * @return List
     */
    List<ServerDelayInstanceResponse> toServerDelayPullResponseList(List<DelayInstancePullResponseDTO> pullList);
}
