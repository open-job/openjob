package io.openjob.worker.request;

import io.openjob.common.util.TaskUtil;
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
    private Long circleId;
    private Integer status;
    private String workerAddress;
    private String masterActorPath;
    private String result;

    /**
     * New container task request.
     */
    public ContainerTaskStatusRequest() {
        this.jobId = 0L;
        this.jobInstanceId = 0L;
        this.taskId = 0L;
        this.circleId = 0L;
    }

    public String getTaskUniqueId() {
        return TaskUtil.getRandomUniqueId(this.jobId, this.jobInstanceId, this.circleId, this.taskId);
    }
}
