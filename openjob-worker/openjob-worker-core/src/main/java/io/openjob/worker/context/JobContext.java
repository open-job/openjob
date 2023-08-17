package io.openjob.worker.context;

import io.openjob.common.constant.TaskConstant;
import io.openjob.common.dto.JobInstanceDTO;
import io.openjob.worker.constant.WorkerConstant;
import io.openjob.worker.processor.TaskResult;
import lombok.Data;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class JobContext {
    private Long delayId;
    private Long delayPid;
    private String delayTopic;
    private String delayTaskId;
    private String delayParams;
    private String delayExtra;
    private Long jobId;
    private Long jobInstanceId;
    private Long dispatchVersion;
    private Long taskId;
    private String jobParamType;
    private String jobParams;
    private String jobExtendParamsType;
    private String jobExtendParams;
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
    private Long circleId;

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
    private String timeExpressionType;
    private String timeExpression;

    /**
     * Default
     */
    public JobContext() {
        this.circleId = 0L;
        this.shardingNum = 0;
        this.failAttemptTimes = 0;
    }

    /**
     * @return Boolean
     */
    public Boolean isRoot() {
        return TaskConstant.MAP_TASK_ROOT_NAME.equals(this.taskName);
    }

    /**
     * @param taskName task name.
     * @return Boolean
     */
    public Boolean isTask(String taskName) {
        return this.taskName.equals(taskName);
    }
}
