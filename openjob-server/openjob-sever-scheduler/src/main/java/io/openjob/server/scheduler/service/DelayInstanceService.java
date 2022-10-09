package io.openjob.server.scheduler.service;

import io.openjob.common.request.WorkerDelayPullItemRequest;
import io.openjob.common.request.WorkerDelayPullRequest;
import io.openjob.common.response.ServerDelayInstanceResponse;
import io.openjob.common.response.ServerDelayPullResponse;
import io.openjob.server.scheduler.dto.DelayDTO;
import io.openjob.server.scheduler.util.CacheUtil;
import io.openjob.server.scheduler.util.RedisUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class DelayInstanceService {
    public ServerDelayPullResponse pullInstance(WorkerDelayPullRequest pullRequest) {
        List<ServerDelayInstanceResponse> responses = new ArrayList<>();

        // Pull delay instance.
        pullRequest.getPullItems().forEach(item -> responses.addAll(this.pullByTopic(item)));

        ServerDelayPullResponse delayPullResponse = new ServerDelayPullResponse();
        delayPullResponse.setDelayInstanceResponses(responses);
        return delayPullResponse;
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
