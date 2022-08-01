package io.openjob.worker.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class ContainerTaskStatusRequest implements Serializable {
    private Long jobId;
    private Long jobInstanceId;
    private Long taskId;
    private Integer status;
    private String workerAddress;
    private String masterActorPath;
    private String result;
}
