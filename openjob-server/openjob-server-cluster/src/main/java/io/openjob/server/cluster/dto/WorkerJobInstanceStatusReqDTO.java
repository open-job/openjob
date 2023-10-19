package io.openjob.server.cluster.dto;

import io.openjob.common.constant.FailStatusEnum;
import io.openjob.common.constant.InstanceStatusEnum;
import lombok.Data;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.8
 */
@Data
public class WorkerJobInstanceStatusReqDTO {
    /**
     * Job id.
     */
    private Long jobId;

    /**
     * Job instance id.
     */
    private Long jobInstanceId;

    /**
     * Current circleId.
     * Only for second delay task.
     */
    private Long circleId;

    /**
     * Job instance status.
     *
     * @see InstanceStatusEnum
     */
    private Integer status;

    /**
     * Fail status
     *
     * @see FailStatusEnum#getStatus()
     */
    private Integer failStatus;

    /**
     * Result
     */
    private String result;
}
