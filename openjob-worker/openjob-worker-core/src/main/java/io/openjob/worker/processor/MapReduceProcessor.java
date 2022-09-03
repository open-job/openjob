package io.openjob.worker.processor;

import io.openjob.worker.context.JobContext;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface MapReduceProcessor extends MapProcessor {
    /**
     *
     * @param jobContext
     * @return
     */
    ProcessResult reduce(JobContext jobContext);
}
