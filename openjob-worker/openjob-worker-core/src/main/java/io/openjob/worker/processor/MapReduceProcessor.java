package io.openjob.worker.processor;

import io.openjob.worker.context.JobContext;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public interface MapReduceProcessor extends MapProcessor {
    /**
     * Processor reduce.
     *
     * @param jobContext jobContext
     * @return ProcessResult
     */
    ProcessResult reduce(JobContext jobContext);
}
