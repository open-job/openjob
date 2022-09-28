package io.openjob.server.log.entity;

import io.openjob.common.constant.LogFieldConstant;
import io.openjob.common.request.WorkerJobInstanceTaskLogFieldRequest;
import lombok.Data;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class JobInstanceTaskLog {
    private Long jobId;
    private Long jobInstanceId;
    private Long circleId;
    private Long taskId;
    private String taskUniqueId;
    private String workerAddress;
    private String content;
    private List<WorkerJobInstanceTaskLogFieldRequest> fields;
    private Long time;
}
