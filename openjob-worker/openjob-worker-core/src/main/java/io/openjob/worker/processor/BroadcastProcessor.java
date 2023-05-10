package io.openjob.worker.processor;

import io.openjob.worker.context.JobContext;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class BroadcastProcessor implements JobProcessor {
    @Override
    public ProcessResult process(JobContext context) throws Exception {
        return null;
    }

    @Override
    public void stop(JobContext context) {

    }

    @Override
    public void preProcess(JobContext context) throws Exception {

    }

    @Override
    public ProcessResult postProcess(JobContext context) throws Exception {
        return null;
    }
}
