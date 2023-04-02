package io.openjob.server.scheduler.dto;

import lombok.Data;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class TopicFailCounterDTO {
    private String topic;
    private Long count;
}
