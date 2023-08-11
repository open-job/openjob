package io.openjob.common.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Data
public class WorkerJobInstanceTaskBatchRequest implements Serializable {

    /**
     * Delivery id.
     */
    private Long deliveryId;

    /**
     * Job instance task list.
     * Aggregation many circle task, if second delay task.
     */
    private List<WorkerJobInstanceTaskRequest> taskRequestList;
}
