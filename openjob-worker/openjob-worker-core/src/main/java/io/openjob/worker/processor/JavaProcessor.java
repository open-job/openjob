package io.openjob.worker.processor;

import io.openjob.worker.context.JobContext;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public abstract class JavaProcessor implements JobProcessor {
    @Override
    public void preProcess(JobContext context) throws Exception {

    }

    @Override
    public ProcessResult process(JobContext context) throws Exception {
        return null;
    }

    @Override
    public ProcessResult postProcess(JobContext context) throws Exception {
        return null;
    }

    @Override
    public void kill(JobContext context) {

    }
}
