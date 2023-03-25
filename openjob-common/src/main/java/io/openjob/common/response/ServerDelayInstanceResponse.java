package io.openjob.common.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class ServerDelayInstanceResponse implements Serializable {
    private String topic;
    private Long delayId;
    private Long delayPid;
    private String taskId;
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
