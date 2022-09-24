package io.openjob.common.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class WorkerJobInstanceTaskLogRequest implements Serializable {
    private Long jobInstanceId;
    private Long circleId;
    private Long taskId;
    private String workerAddress;
    private List<WorkerJobInstanceTaskLogFieldRequest> fieldList;
}
