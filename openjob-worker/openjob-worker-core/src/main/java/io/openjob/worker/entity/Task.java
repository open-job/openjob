package io.openjob.worker.entity;

import lombok.Data;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class Task {
    private Long jobId;
    private Long jobInstanceId;
    private Long taskId;
    private String taskName;
    private Integer status;
    private String workerAddress;
    private Integer createTime;
    private Integer updateTime;
}
