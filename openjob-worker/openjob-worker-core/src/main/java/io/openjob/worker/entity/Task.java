package io.openjob.worker.entity;

import lombok.Data;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class Task {
    private Long id;
    private Long jobId;
    private Long instanceId;
    private Long circleId;
    private String taskId;
    private String taskName;
    private String taskParentId;
    private Integer status;
    private String workerAddress;
    private byte[] task_body;
    private Integer createTime;
    private Integer updateTime;
}
