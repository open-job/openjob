package io.openjob.worker.container;

import io.openjob.worker.context.JobContext;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public abstract class BaseTaskContainer implements TaskContainer {
    protected JobContext jobContext;

    public BaseTaskContainer(JobContext jobContext) {
        this.jobContext = jobContext;
    }
}
