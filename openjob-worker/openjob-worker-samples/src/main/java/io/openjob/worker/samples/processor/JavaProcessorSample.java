package io.openjob.worker.samples.processor;

import io.openjob.worker.context.JobContext;
import io.openjob.worker.processor.JavaProcessor;
import io.openjob.worker.processor.ProcessResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class JavaProcessorSample implements JavaProcessor {
    private static final Logger logger = LoggerFactory.getLogger("openjob");

    @Override
    public ProcessResult process(JobContext context) throws Exception {
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        System.out.println(context.getJobParams());

        for (int i = 0; i < 128; i++) {
            logger.info("JavaProcessorTest execute success! instanceId=" + context.getJobInstanceId() + " time=" + sdf.format(date));
            Thread.sleep(1000L);
        }
        return new ProcessResult(true);
    }
}
