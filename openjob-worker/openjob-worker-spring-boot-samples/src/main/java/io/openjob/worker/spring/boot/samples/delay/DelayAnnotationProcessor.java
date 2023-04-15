package io.openjob.worker.spring.boot.samples.delay;

import io.openjob.worker.context.JobContext;
import io.openjob.worker.processor.ProcessResult;
import io.openjob.worker.spring.boot.annotation.Openjob;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class DelayAnnotationProcessor {
    @Openjob("annotationDelay")
    public ProcessResult annotationDelay(JobContext context) {
        return ProcessResult.success();
    }
}
