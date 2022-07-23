package io.openjob.common.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class WorkerStartContainerRequest implements Serializable {
    private Long jobId;
    private Long jobInstanceId;
    private Long taskId;
}
