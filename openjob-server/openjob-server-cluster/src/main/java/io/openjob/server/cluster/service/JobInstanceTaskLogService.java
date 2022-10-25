package io.openjob.server.cluster.service;

import io.openjob.common.constant.LogFieldConstant;
import io.openjob.common.request.WorkerJobInstanceTaskLogFieldRequest;
import io.openjob.common.request.WorkerJobInstanceTaskLogRequest;
import io.openjob.common.util.TaskUtil;
import io.openjob.server.log.dao.LogDAO;
import io.openjob.server.log.entity.JobInstanceTaskLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
@Service
public class JobInstanceTaskLogService {
    private final LogDAO logDAO;

    @Autowired
    public JobInstanceTaskLogService(LogDAO logDAO) {
        this.logDAO = logDAO;
    }

    /**
     * Handle instance log.
     * @param logReq log request.
     */
    public void handleInstanceTaskLog(WorkerJobInstanceTaskLogRequest logReq) {
        List<JobInstanceTaskLog> taskLogs = new ArrayList<>();

        String taskUniqueId = TaskUtil.getRandomUniqueId(logReq.getJobId(), logReq.getJobInstanceId(), logReq.getCircleId(), logReq.getTaskId());
        logReq.getFieldList().forEach(fields -> {
            JobInstanceTaskLog jobInstanceTaskLog = new JobInstanceTaskLog();
            jobInstanceTaskLog.setJobId(logReq.getJobId());
            jobInstanceTaskLog.setJobInstanceId(logReq.getJobInstanceId());
            jobInstanceTaskLog.setCircleId(logReq.getCircleId());
            jobInstanceTaskLog.setTaskId(logReq.getTaskId());
            jobInstanceTaskLog.setTaskUniqueId(taskUniqueId);
            jobInstanceTaskLog.setWorkerAddress(logReq.getWorkerAddress());

            WorkerJobInstanceTaskLogFieldRequest field = fields.stream()
                    .filter(c -> LogFieldConstant.TIME_STAMP.equals(c.getName()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Don't have `timeStamp` field!"));
            jobInstanceTaskLog.setTime(Long.valueOf(field.getValue()));
            jobInstanceTaskLog.setFields(fields);
            taskLogs.add(jobInstanceTaskLog);
        });

        try {
            logDAO.batchAdd(taskLogs);
        } catch (SQLException e) {
            log.error("Batch add task log failed!", e);
            throw new RuntimeException(e);
        }
    }
}
