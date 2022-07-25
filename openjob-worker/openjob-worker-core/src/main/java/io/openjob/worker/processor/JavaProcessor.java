package io.openjob.worker.processor;

import io.openjob.worker.context.JobContext;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class JavaProcessor implements JobProcessor {
    @Override
    public void preProcess(JobContext context) {

    }

    @Override
    public ProcessResult process(JobContext context) {
        return null;
    }

    @Override
    public ProcessResult postProcess(JobContext context) {
        return null;
    }

    @Override
    public void stop(JobContext context) {

    }
}
