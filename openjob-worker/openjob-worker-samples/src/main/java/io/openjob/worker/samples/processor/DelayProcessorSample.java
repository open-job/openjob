package io.openjob.worker.samples.processor;

import io.openjob.worker.context.JobContext;
import io.openjob.worker.processor.JavaProcessor;
import io.openjob.worker.processor.ProcessResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
public class DelayProcessorSample implements JavaProcessor {

    private static final Logger logger = LoggerFactory.getLogger("openjob");

    @Override
    public ProcessResult process(JobContext context) throws InterruptedException {
        logger.info("Delay run {} {} {}", context.getDelayTaskId(), context.getDelayParams(), context.getDelayExtra());
        log.info("Delay run {} {} {}", context.getDelayTaskId(), context.getDelayParams(), context.getDelayExtra());

//        throw new RuntimeException("test");
//        for (int i = 0; i < 16; i++) {
//            logger.info("JavaProcessorTest execute success! taskId={}", context.getDelayTaskId());
//            Thread.sleep(2000L);
//        }
        return new ProcessResult(true);
    }
}
