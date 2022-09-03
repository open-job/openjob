package io.openjob.worker.processor;

import io.openjob.worker.context.JobContext;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface JavaProcessor extends JobProcessor {

    /**
     * Preprocess.
     *
     * @param context context
     */
    @Override
    default void preProcess(JobContext context) {

    }

    /**
     * Process
     *
     * @param context job context.
     * @return ProcessResult
     */
    ProcessResult process(JobContext context);

    /**
     * Postprocess
     *
     * @param context context
     * @return ProcessResult
     */
    @Override
    default ProcessResult postProcess(JobContext context) {
        return null;
    }

    /**
     * Stop
     *
     * @param context context
     */
    default void stop(JobContext context) {

    }
}
