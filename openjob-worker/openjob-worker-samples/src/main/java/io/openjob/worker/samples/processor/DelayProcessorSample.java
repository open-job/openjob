package io.openjob.worker.samples.processor;

import io.openjob.worker.context.JobContext;
import io.openjob.worker.processor.JavaProcessor;
import io.openjob.worker.processor.ProcessResult;
import lombok.extern.slf4j.Slf4j;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
public class DelayProcessorSample implements JavaProcessor {
    @Override
    public ProcessResult process(JobContext context) {
        log.info("Delay run {} {}", context.getDelayParams(), context.getDelayExtra());
        return new ProcessResult(true);
    }
}
