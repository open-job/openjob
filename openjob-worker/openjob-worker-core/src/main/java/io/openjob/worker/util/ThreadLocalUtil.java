package io.openjob.worker.util;

import io.openjob.worker.context.JobContext;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ThreadLocalUtil {
    private static final ThreadLocal<JobContext> JOB_CONTEXT_LOCAL = new ThreadLocal<>();


    public static void setJobContext(JobContext jobContext) {
        JOB_CONTEXT_LOCAL.set(jobContext);
    }

    public static JobContext getJobContext() {
        return JOB_CONTEXT_LOCAL.get();
    }
}
