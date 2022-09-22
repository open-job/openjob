package io.openjob.worker.container;

import io.openjob.worker.context.JobContext;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface TaskContainer {

    void execute(JobContext jobContext);

    void stop();

    void destroy();
}
