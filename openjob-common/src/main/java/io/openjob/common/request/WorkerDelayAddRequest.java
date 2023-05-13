package io.openjob.common.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class WorkerDelayAddRequest implements Serializable {

    /**
     * Deliver id.
     */
    private Long deliveryId;

    /**
     * Delay task unique id.
     * If is null or blank, will to auto generate.
     */
    private String taskId;

    /**
     * Topic
     */
    private String topic;

    /**
     * Delay task params.
     */
    private String params;

    /**
     * Delay task extra params.
     */
    private String extra;

    /**
     * Delay task execute time.
     */
    private Long executeTime;
}
