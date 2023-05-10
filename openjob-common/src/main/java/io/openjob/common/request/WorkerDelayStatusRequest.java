package io.openjob.common.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class WorkerDelayStatusRequest implements Serializable {

    /**
     * Delivery id.
     */
    private Long deliveryId;

    /**
     * Delay task list.
     */
    private List<WorkerDelayTaskRequest> taskList;
}
