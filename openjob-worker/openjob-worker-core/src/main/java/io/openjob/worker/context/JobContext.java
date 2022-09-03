package io.openjob.worker.context;

import io.openjob.common.dto.JobInstanceDTO;
import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.worker.constant.WorkerConstant;
import io.openjob.worker.processor.TaskResult;
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
    private String taskName;
    private String executeType;
    private String processorType;
    private String processorInfo;
    private String masterActorPath;
    private Object task;

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

    private List<TaskResult> taskResultList;

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

    /**
     * Default
     */
    public JobContext() {
        this.serialNum = 0L;
        this.shardingNum = 0;
        this.failAttemptTimes = 0;
    }

    /**
     * @return Boolean
     */
    public Boolean isRoot() {
        return WorkerConstant.MAP_TASK_ROOT_NAME.equals(this.taskName);
    }

    /**
     * @param taskName task name.
     * @return Boolean
     */
    public Boolean isTask(String taskName) {
        return this.taskName.equals(taskName);
    }
}
