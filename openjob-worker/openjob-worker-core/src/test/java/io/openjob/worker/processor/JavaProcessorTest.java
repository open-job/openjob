package io.openjob.worker.processor;

import io.openjob.worker.context.JobContext;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class JavaProcessorTest extends JavaProcessor {
    @Override
    public ProcessResult process(JobContext context) {
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

        System.out.println("JavaProcessorTest execute success! instanceId=" + context.getJobInstanceId() + " time=" + sdf.format(date));
        return new ProcessResult(true);
    }
}
