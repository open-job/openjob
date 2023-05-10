package io.openjob.server.repository.dto;

import lombok.Data;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class DelayInstanceTotalDTO {
    private String topic;
    private Long total;

    public DelayInstanceTotalDTO(String topic, Long total) {
        this.topic = topic;
        this.total = total;
    }
}
