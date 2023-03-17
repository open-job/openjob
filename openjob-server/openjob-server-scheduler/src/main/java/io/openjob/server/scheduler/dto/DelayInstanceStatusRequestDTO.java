package io.openjob.server.scheduler.dto;

import lombok.Data;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class DelayInstanceStatusRequestDTO {

    /**
     * Topic
     */
    private String topic;

    /**
     * Delay id.
     */
    private Long delayId;

    /**
     * Task id.
     */
    private String taskId;

    /**
     * Task status.
     */
    private Integer status;

    /**
     * Task result.
     */
    private String result;

    /**
     * Zset cache key
     */
    private String zsetKey;

    /**
     * Worker address
     */
    private String workerAddress;
}
