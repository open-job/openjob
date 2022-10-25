package io.openjob.worker.request;

import io.openjob.common.util.TaskUtil;
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

    private Long parentTaskId;

    /**
     * Sharding id.
     */
    private Long shardingId;

    /**
     * Sharding param.
     */
    private String shardingParam;

    /**
     * Sharding num.
     */
    private Integer shardingNum;

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

    /**
     * New MasterStartContainerRequest
     */
    public MasterStartContainerRequest() {
        this.jobId = 0L;
        this.jobInstanceId = 0L;
        this.taskId = 0L;
        this.circleId = 0L;
        this.parentTaskId = 0L;
        this.taskName = "";
    }

    public String getTaskUniqueId() {
        return TaskUtil.getRandomUniqueId(this.jobId, this.jobInstanceId, this.circleId, this.taskId);
    }

    public String getParentTaskUniqueId() {
        return TaskUtil.getRandomUniqueId(this.jobId, this.jobInstanceId, this.circleId, this.parentTaskId);
    }
}
