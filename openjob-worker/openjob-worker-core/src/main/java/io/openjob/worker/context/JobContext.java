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
    private Long wfInstanceId;
    private Long taskId = 0L;
    private String jobName;
    private Integer scheduleTime;
    private Integer dataTime;
    private String executeMode;
    private String jobType;
    private String instanceMasterActorPath;
    private String taskName;
    private Object task;
    private String groupId;
    private String content;
    private String user;

    /**
     * Max retry times.
     */
    private Integer maxAttempt;

    /**
     * Current retry times.
     */
    private Integer attempt;

    /**
     * Job custom params.
     */
    private String jobParams;

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
     * Task max retry times.
     */
    private Integer taskMaxAttempt;

    /**
     * Task current retry times.
     */
    private Integer taskAttempt = 0;

    /**
     * Task retry interval.
     */
    private Integer taskAttemptInterval;

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
    private Integer shardingNum = 0;

    private List<String> allWorkerAddresses;
    private String workerAddress;

    private Integer timeType;
    private String timeExpression;
}
