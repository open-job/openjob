package io.openjob.worker.master;

import io.openjob.common.request.ServerInstanceTaskChildListPullRequest;
import io.openjob.common.request.ServerInstanceTaskListPullRequest;
import io.openjob.common.request.ServerStopInstanceTaskRequest;
import io.openjob.common.response.WorkerInstanceTaskChildListPullResponse;
import io.openjob.common.response.WorkerInstanceTaskListPullResponse;
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
     *
     * @param type type
     */
    void stop(Integer type);

    /**
     * Pull instance task
     *
     * @param request request
     * @return WorkerInstanceTaskListPullResponse
     */
    WorkerInstanceTaskListPullResponse pullInstanceTaskList(ServerInstanceTaskListPullRequest request);

    /**
     * Pull instance child task
     *
     * @param request request
     * @return WorkerInstanceTaskChildListPullResponse
     */
    WorkerInstanceTaskChildListPullResponse pullInstanceTaskChildList(ServerInstanceTaskChildListPullRequest request);

    /**
     * Stop task
     *
     * @param request request
     */
    void stopTask(ServerStopInstanceTaskRequest request);
}
