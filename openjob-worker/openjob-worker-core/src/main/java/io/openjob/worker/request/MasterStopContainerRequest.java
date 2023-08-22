package io.openjob.worker.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class MasterStopContainerRequest implements Serializable {
    private Long deliveryId;
    private Long jobId;
    private Long jobInstanceId;
    private String workerAddress;
    private Long taskId;
    private Integer stopType;
}
