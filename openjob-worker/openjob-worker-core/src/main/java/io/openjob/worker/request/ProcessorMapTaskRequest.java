package io.openjob.worker.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class ProcessorMapTaskRequest implements Serializable {
    private long jobId;
    private long jobInstanceId;
    private long taskId;
    private String taskName;
    private List<Object> tasks;
}
