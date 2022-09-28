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
        logContent.addJobIdField(context.getJobId());
        logContent.setUniqueId(String.format("%d_%d_%d", context.getJobInstanceId(), context.getCircleId(), context.getTaskId()));
        logContent.addJobInstanceIdField(context.getJobInstanceId());
        logContent.addTaskIdField(context.getTaskId());
        logContent.addCircleIdField(context.getCircleId());
        logContent.addWorkerAddressField("");
        return logContent;
    }
}
