package io.openjob.server.scheduler.dto;

import lombok.Data;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class DelayTopicPullResponseDTO {
    private List<DelayTopicPullDTO> topicList;
}
