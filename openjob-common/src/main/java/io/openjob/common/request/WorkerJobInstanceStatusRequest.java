package io.openjob.common.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

import io.openjob.common.constant.InstanceStatusEnum;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class WorkerJobInstanceStatusRequest implements Serializable {

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
     * Delivery id.
     */
    private Long deliveryId;

    /**
     * Current page.
     */
    private Long page;

    /**
     * Job instance task list.
     * Aggregation many circle task, if second delay task.
     */
    private List<WorkerJobInstanceTaskRequest> taskRequestList;
}
