package io.openjob.worker.entity;

import lombok.Data;

/**
 * @author stelin swoft@qq.com
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
    private String result;
    private byte[] taskBody;
    private Long createTime;
    private Long updateTime;

    public Task() {
    }

    public Task(String taskId, Integer status) {
        this.taskId = taskId;
        this.status = status;
    }

    public Task(String taskId, Integer status, String result) {
        this.taskId = taskId;
        this.status = status;
        this.result = result;
    }
}
