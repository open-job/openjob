package io.openjob.worker.processor;

import io.openjob.worker.context.JobContext;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface JavaProcessor extends JobProcessor {
    @Override
    default void preProcess(JobContext context) {

    }

    ProcessResult process(JobContext context);

    default ProcessResult postProcess(JobContext context) {
        return null;
    }

    default void stop(JobContext context) {

    }
}
