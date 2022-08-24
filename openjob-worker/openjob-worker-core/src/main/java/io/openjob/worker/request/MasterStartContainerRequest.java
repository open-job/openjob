package io.openjob.worker.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class MasterStartContainerRequest implements Serializable {
    private Long jobId;

    private Long jobInstanceId;

    private Long taskId;

    private Long circleId;
    private String jobParams;
    private String executeType;
    private Long workflowId;
    private String processorType;
    private String processorInfo;
    private Integer failRetryTimes;
    private Integer failRetryInterval;
    private Integer concurrency;
    private String timeExpressionType;
    private String timeExpression;
    private String masterAkkaPath;
    private String taskName;

    private byte[] task;
    private List<String> workerAddresses;

    public MasterStartContainerRequest() {
        this.jobId = 0L;
        this.jobInstanceId = 0L;
        this.taskId = 0L;
        this.circleId = 0L;
    }

    public String getTaskUniqueId() {
        return String.format("%d_%d_%d_%d", this.jobId, this.jobInstanceId, this.taskId, this.circleId);
    }
}
