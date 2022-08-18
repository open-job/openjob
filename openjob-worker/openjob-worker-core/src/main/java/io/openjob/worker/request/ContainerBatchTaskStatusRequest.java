package io.openjob.worker.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class ContainerBatchTaskStatusRequest implements Serializable {
    private Long jobId;
    private Long jobInstanceId;
    private String workerAddress;
    private String masterActorPath;
    private Long deliveryId;
    private List<ContainerTaskStatusRequest> tasks;
}
