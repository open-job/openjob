package io.openjob.worker.master;

import io.openjob.worker.request.ContainerBatchTaskStatusRequest;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface TaskMaster {
    void submit();

    Boolean isTaskComplete(Long instanceId, Long circleId);

    void updateStatus(ContainerBatchTaskStatusRequest batchRequest);

    void stop();
}
