package io.openjob.worker.processor;

import io.openjob.common.constant.TaskStatusEnum;
import lombok.Data;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class TaskResult {
    private Long jobInstanceId;
    private Long circleId;
    private String taskId;
    private String parentTaskId;
    private String result;
    private String taskName;
    private Integer status;
}
