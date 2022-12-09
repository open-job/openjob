package io.openjob.common.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class WorkerJobInstanceTaskRequest implements Serializable {

    /**
     * Job id.
     */
    private Long jobId;

    /**
     * Job instance id.
     */
    private Long jobInstanceId;

    /**
     * Only for second delay task.
     */
    private Long circleId;

    /**
     * Task unique id.
     * like 'jobId_instanceId_taskId_circleId'
     */
    private String taskId;

    /**
     * Task parent unique id.
     * like 'jobId_instanceId_taskId_circleId'
     */
    private String parentTaskId;

    /**
     * Task name.
     * Only for map reduce task.
     */
    private String taskName;

    /**
     * Task status.
     */
    private Integer status;

    /**
     * Task result.
     */
    private String result;

    /**
     * Task worker address
     */
    private String workerAddress;

    /**
     * Task create time.
     */
    private Long createTime;

    /**
     * Task update time.
     */
    private Long updateTime;
}
