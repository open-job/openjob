package io.openjob.worker.request;

import io.openjob.common.constant.CommonConstant;
import io.openjob.common.constant.TaskConstant;
import io.openjob.common.util.TaskUtil;
import lombok.Data;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class MasterStartContainerRequest implements Serializable {
    private Long jobId;

    private Long jobInstanceId;

    private Long dispatchVersion;

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
    private String jobParamType;
    private String jobParams;
    private String jobExtendParamsType;
    private String jobExtendParams;
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

    private Long mapTaskId;
    private Integer persistent;

    /**
     * New MasterStartContainerRequest
     */
    public MasterStartContainerRequest() {
        this.jobId = 0L;
        this.jobInstanceId = 0L;
        this.dispatchVersion = 0L;
        this.taskId = 0L;
        this.circleId = 0L;
        this.parentTaskId = 0L;
        this.taskName = "";
        this.persistent = CommonConstant.YES;
    }

    public String getTaskUniqueId() {
        return TaskUtil.getRandomUniqueId(this.jobId, this.jobInstanceId, this.dispatchVersion, this.circleId, this.taskId);
    }

    public String getParentTaskUniqueId() {
        if (NumberUtils.LONG_ZERO.equals(this.parentTaskId)) {
            return TaskConstant.DEFAULT_PARENT_ID;
        }

        return TaskUtil.getRandomUniqueId(this.jobId, this.jobInstanceId, this.dispatchVersion, this.circleId, this.parentTaskId);
    }
}
