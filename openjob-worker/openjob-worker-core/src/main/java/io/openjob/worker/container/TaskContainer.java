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
     *
     * @param type type
     */
    void stop(Integer type);

    /**
     * Stop one task
     *
     * @param taskId taskId
     */
    void stopTask(String taskId);

    /**
     * Destroy
     */
    void destroy();
}
