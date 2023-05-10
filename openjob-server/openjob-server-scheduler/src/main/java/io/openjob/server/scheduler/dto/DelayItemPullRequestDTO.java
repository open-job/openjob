package io.openjob.server.scheduler.dto;

import lombok.Data;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class DelayItemPullRequestDTO {
    private String topic;
    private Integer size;
}
