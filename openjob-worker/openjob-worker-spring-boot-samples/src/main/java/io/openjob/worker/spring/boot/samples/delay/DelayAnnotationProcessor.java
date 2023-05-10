package io.openjob.worker.spring.boot.samples.delay;

import io.openjob.worker.context.JobContext;
import io.openjob.worker.processor.ProcessResult;
import io.openjob.worker.spring.boot.annotation.Openjob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Component
public class DelayAnnotationProcessor {
    private static final Logger logger = LoggerFactory.getLogger("openjob");

    @Openjob("annotationDelay")
    public ProcessResult annotationDelay(JobContext context) {
        logger.info("Delay annotation processor execute success!");
        return ProcessResult.success();
    }
}
