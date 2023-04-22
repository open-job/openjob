package io.openjob.worker.spring.boot.samples.processor;

import io.openjob.worker.context.JobContext;
import io.openjob.worker.processor.JavaProcessor;
import io.openjob.worker.processor.ProcessResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class StandaloneProcessor implements JavaProcessor {
    private static final Logger logger = LoggerFactory.getLogger("openjob");

    @Override
    public ProcessResult process(JobContext context) throws Exception {
        String logMessage = "Standalone processor execute success! jobParamsType={} jobParams={} jobExtendParamsType={} jobExtendParams={}";
        logger.info(logMessage, context.getJobParamType(), context.getJobParams(), context.getJobExtendParamsType(), context.getJobExtendParams());
        return ProcessResult.success();
    }
}
