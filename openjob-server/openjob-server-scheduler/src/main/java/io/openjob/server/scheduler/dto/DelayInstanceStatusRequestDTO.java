package io.openjob.server.scheduler.dto;

import io.openjob.common.constant.FailStatusEnum;
import lombok.Data;

/**
 * @author stelin swoft@qq.com
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
     * Delay pid
     */
    private Long delayPid;

    /**
     * Task id.
     */
    private String taskId;

    /**
     * Task status.
     */
    private Integer status;

    /**
     * Fail status
     *
     * @see FailStatusEnum#getStatus()
     */
    private Integer failStatus;

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

    /**
     * Complete time
     */
    private Long completeTime;
}
