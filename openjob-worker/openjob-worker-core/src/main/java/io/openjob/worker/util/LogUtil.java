package io.openjob.worker.util;

import io.openjob.common.util.TaskUtil;
import io.openjob.worker.OpenjobWorker;
import io.openjob.worker.context.JobContext;
import io.openjob.worker.dto.LogContentDTO;
import io.openjob.worker.init.WorkerConfig;

import java.util.Objects;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class LogUtil {

    /**
     * Get log content.
     *
     * @return LogContentDTO
     */
    public static LogContentDTO getLogContent() {
        // Job context
        JobContext context = ThreadLocalUtil.getJobContext();

        // Cron job.
        LogContentDTO logContent = new LogContentDTO();
        logContent.addWorkerAddressField(WorkerConfig.getWorkerAddress());
        if (Objects.isNull(context.getDelayTaskId())) {
            logContent.addJobIdField(context.getJobId());
            logContent.addJobInstanceIdField(context.getJobInstanceId());
            logContent.addCircleIdField(context.getCircleId());
            logContent.addJobInstanceTaskIdField(context.getTaskId());
            String taskId = TaskUtil.getRandomUniqueId(context.getJobId(), context.getJobInstanceId(), context.getCircleId(), context.getTaskId());
            logContent.addTaskIdField(taskId);
            return logContent;
        }

        // Delay job.
        logContent.addTaskIdField(context.getDelayTaskId());
        logContent.addDelayTopic(context.getDelayTopic());
        return logContent;
    }
}
