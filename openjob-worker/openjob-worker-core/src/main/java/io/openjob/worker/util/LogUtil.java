package io.openjob.worker.util;

import io.openjob.worker.context.JobContext;
import io.openjob.worker.dto.LogContentDTO;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class LogUtil {
    public static LogContentDTO getLogContent() {
        JobContext context = ThreadLocalUtil.getJobContext();

        LogContentDTO logContent = new LogContentDTO();
        logContent.setUniqueId(String.format("%d_%d_%d", context.getJobInstanceId(), context.getCircleId(), context.getTaskId()));
        logContent.setJobInstanceId(context.getJobInstanceId());
        logContent.setTaskId(context.getTaskId());
        logContent.setCircleId(context.getCircleId());
        return logContent;
    }
}
