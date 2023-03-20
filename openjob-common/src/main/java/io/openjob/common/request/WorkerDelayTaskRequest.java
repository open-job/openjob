package io.openjob.common.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class WorkerDelayTaskRequest implements Serializable {

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
     * Worker address
     */
    private String workerAddress;

    /**
     * Complete time
     */
    private Long completeTime;
}
