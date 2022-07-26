package io.openjob.worker.processor;

import io.openjob.worker.context.JobContext;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class JavaProcessorTest extends JavaProcessor {
    @Override
    public ProcessResult process(JobContext context) {
        System.out.println("JavaProcessorTest execute! context=" + context.toString());
        return new ProcessResult(true);
    }
}
