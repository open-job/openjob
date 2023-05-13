package io.openjob.worker.container;

import io.openjob.worker.context.JobContext;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public interface TaskContainer {

    /**
     * Execute
     *
     * @param jobContext jobContext
     */
    void execute(JobContext jobContext);

    /**
     * Stop
     */
    void stop();

    /**
     * Destroy
     */
    void destroy();
}
