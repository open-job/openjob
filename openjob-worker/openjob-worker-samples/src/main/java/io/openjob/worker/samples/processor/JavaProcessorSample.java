package io.openjob.worker.samples.processor;

import io.openjob.worker.context.JobContext;
import io.openjob.worker.processor.JavaProcessor;
import io.openjob.worker.processor.ProcessResult;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class JavaProcessorSample implements JavaProcessor {
    @Override
    public ProcessResult process(JobContext context) {
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

        System.out.println("JavaProcessorTest execute success! instanceId=" + context.getJobInstanceId() + " time=" + sdf.format(date));
        return new ProcessResult(true);
    }
}
