package io.openjob.worker.spring.boot.samples.delay;

import io.openjob.worker.context.JobContext;
import io.openjob.worker.processor.JavaProcessor;
import io.openjob.worker.processor.ProcessResult;
import org.springframework.stereotype.Component;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Component
public class DelayProcessor implements JavaProcessor {
    @Override
    public ProcessResult process(JobContext context) throws Exception {
        return ProcessResult.success();
    }
}
