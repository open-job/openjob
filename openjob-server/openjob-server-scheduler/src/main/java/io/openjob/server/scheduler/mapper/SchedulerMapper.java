package io.openjob.server.scheduler.mapper;

import io.openjob.common.request.WorkerDelayTopicPullRequest;
import io.openjob.common.response.ServerDelayTopicResponse;
import io.openjob.server.repository.entity.Delay;
import io.openjob.server.scheduler.dto.DelayTopicPullDTO;
import io.openjob.server.scheduler.dto.DelayTopicPullRequestDTO;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface SchedulerMapper {
    SchedulerMapper INSTANCE = Mappers.getMapper(SchedulerMapper.class);

    DelayTopicPullDTO toDelayTopicPullDTO(Delay delay);

    DelayTopicPullRequestDTO toDelayTopicPullRequestDTO(WorkerDelayTopicPullRequest request);

    List<ServerDelayTopicResponse> toServerDelayTopicResponseList(List<DelayTopicPullDTO> pullList);
}
