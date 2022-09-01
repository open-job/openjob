package io.openjob.worker.master;

import io.openjob.worker.request.ContainerBatchTaskStatusRequest;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface TaskMaster {
    void submit();

    void completeTask();

    void updateStatus(ContainerBatchTaskStatusRequest batchRequest);

    void stop();
}
