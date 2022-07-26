package io.openjob.worker.context;

import io.openjob.common.dto.JobInstanceDTO;
import io.openjob.worker.constant.TaskStatusEnum;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class JobContext {
    private Long jobId;
    private Long jobInstanceId;
    private Long taskId;
    private String jobParams;
    private String jobName;
    private String executeType;
    private String processorType;
    private String processorInfo;
    private String instanceMasterActorPath;

    private Integer failRetryTimes;

    /**
     * Current fail times.
     */
    private Integer failAttemptTimes;
    private Integer failRetryInterval;

    /**
     * Workflow upstream data.
     */
    private List<JobInstanceDTO> upstreamData;

    /**
     * Child task result.
     */
    private Map<Long, String> taskResults;

    /**
     * Child task status.
     */
    private Map<Long, TaskStatusEnum> taskStatuses;

    /**
     * Execute times for second job.
     */
    private Long serialNum;

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

    private Integer concurrency;

    private List<String> workerAddresses;
    private String timeExpressionType;
    private String timeExpression;

    public JobContext() {
        this.serialNum = 0L;
        this.shardingNum = 0;
        this.failAttemptTimes = 0;
    }
}
