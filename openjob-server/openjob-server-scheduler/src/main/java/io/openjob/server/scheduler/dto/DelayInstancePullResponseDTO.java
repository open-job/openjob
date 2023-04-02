package io.openjob.server.scheduler.dto;

import lombok.Data;

import javax.persistence.Column;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class DelayInstancePullResponseDTO {
    private String topic;
    private String taskId;
    private Long delayId;
    private Long delayPid;
    private String delayParams;
    private String delayExtra;
    private String processorInfo;
    private Integer failRetryTimes;
    private Integer failRetryInterval;
    private Integer executeTimeout;
    private Integer blockingSize;
    private Integer concurrency;
    private Integer failTopicEnable;
    private Integer failTopicConcurrency;
}
