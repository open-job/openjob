package io.openjob.worker.container;

import io.openjob.worker.request.MasterStartContainerRequest;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public abstract class BaseTaskContainer implements TaskContainer {
    protected MasterStartContainerRequest startRequest;

    public BaseTaskContainer(MasterStartContainerRequest startRequest) {
        this.startRequest = startRequest;
    }

    @Override
    public void stopTask(String taskId) {
        TaskContainerManager.INSTANCE.stopAndRemoveTask(taskId);
    }
}
