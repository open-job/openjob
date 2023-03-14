package io.openjob.server.scheduler.dto;

import lombok.Data;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class TopicTotalAndReadyCounterDTO {
    private String topic;
    private Long total;
    private Long ready;
}
