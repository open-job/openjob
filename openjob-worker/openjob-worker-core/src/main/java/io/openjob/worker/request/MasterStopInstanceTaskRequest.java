package io.openjob.worker.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.7
 */
@Data
public class MasterStopInstanceTaskRequest implements Serializable {

    /**
     * Delivery id
     */
    private Long deliveryId;

    /**
     * Job instance id
     */
    private Long jobInstanceId;

    /**
     * Job dispatch version
     */
    private Long dispatchVersion;

    /**
     * Circle id
     */
    private Long circleId;

    /**
     * Task id
     */
    private String taskId;

    /**
     * Worker address
     */
    private String workerAddress;
}
