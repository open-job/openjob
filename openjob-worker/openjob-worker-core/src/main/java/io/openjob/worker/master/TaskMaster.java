package io.openjob.worker.master;

import io.openjob.worker.request.ContainerBatchTaskStatusRequest;

import java.util.Set;

/**
 * @author stelin <swoft@qq.com>
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
     * Offline workers.
     *
     * @param offlineWorkers offline workers.
     */
    void offlineWorkers(Set<String> offlineWorkers);

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
    void stop();
}
