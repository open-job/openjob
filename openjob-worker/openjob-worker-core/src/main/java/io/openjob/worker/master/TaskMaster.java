package io.openjob.worker.master;

import io.openjob.worker.request.ContainerBatchTaskStatusRequest;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public interface TaskMaster {

    /**
     * Submit
     */
    void submit();

    /**
     * Get task running status.
     *
     * @return Boolean
     */
    Boolean getRunning();

    /**
     * Destroy task container.
     */
    void destroyTaskContainer();

    /**
     * Complete task.
     *
     * @throws InterruptedException InterruptedException
     */
    void completeTask() throws InterruptedException;

    /**
     * Update status.
     *
     * @param batchRequest batchRequest
     */
    void updateStatus(ContainerBatchTaskStatusRequest batchRequest);

    /**
     * Stop
     */
    void stop(Integer type);
}
