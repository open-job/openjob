package io.openjob.worker.processor;

import io.openjob.worker.context.JobContext;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public interface BaseProcessor {
    /**
     * Process
     *
     * @param context job context.
     * @return ProcessResult
     * @throws Exception exception.
     */
    ProcessResult process(JobContext context) throws Exception;

    /**
     * stop.
     *
     * @param context context
     * @throws Exception exception
     */
    void stop(JobContext context) throws Exception;
}
