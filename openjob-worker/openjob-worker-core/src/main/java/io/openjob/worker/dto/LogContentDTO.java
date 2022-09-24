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
}
