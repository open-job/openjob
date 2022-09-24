package io.openjob.worker.dto;

import io.openjob.common.constant.LogFieldConstant;
import io.openjob.common.request.WorkerJobInstanceTaskLogFieldRequest;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class LogContentDTO {
    private String uniqueId;
    private Long jobId;
    private Long jobInstanceId;
    private Long circleId;
    private Long taskId;
    private String workerAddress;
    private List<WorkerJobInstanceTaskLogFieldRequest> fieldList = new ArrayList<>();

    public void addField(String name, String value) {
        fieldList.add(new WorkerJobInstanceTaskLogFieldRequest(name, value));
    }

    public void addTimeField(String time) {
        this.addField(LogFieldConstant.TIME, time);
    }

    public void addLevelField(String level) {
        this.addField(LogFieldConstant.LEVEL, level);
    }

    public void addThreadField(String thread) {
        this.addField(LogFieldConstant.THREAD, thread);
    }

    public void addMessageField(String message) {
        this.addField(LogFieldConstant.MESSAGE, message);
    }

    public void addLocationField(String location) {
        this.addField(LogFieldConstant.LOCATION, location);
    }

    public void addThrowableField(String throwable) {
        this.addField(LogFieldConstant.THROWABLE, throwable);
    }

    public void addLogField(String log) {
        this.addField(LogFieldConstant.LOG, log);
    }

    public void addJobIdField(Long jobId) {
        this.jobId = jobId;
        this.addField(LogFieldConstant.JOB_ID, String.valueOf(jobId));
    }

    public void addJobInstanceIdField(Long jobInstanceId) {
        this.jobInstanceId = jobInstanceId;
        this.addField(LogFieldConstant.JOB_INSTANCE_ID, String.valueOf(jobInstanceId));
    }

    public void addTaskIdField(Long taskId) {
        this.taskId = taskId;
        this.addField(LogFieldConstant.TASK_ID, String.valueOf(taskId));
    }

    public void addCircleIdField(Long circleId) {
        this.circleId = circleId;
        this.addField(LogFieldConstant.CIRCLE_ID, String.valueOf(circleId));
    }

    public void addWorkerAddressField(String workerAddress) {
        this.workerAddress = workerAddress;
        this.addField(LogFieldConstant.WORKER_ADDRESS, workerAddress);
    }
}
