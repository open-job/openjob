package io.openjob.server.log.dto;

import io.openjob.common.request.WorkerJobInstanceTaskLogFieldRequest;
import lombok.Data;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class ProcessorLog {
    private String taskId;
    private String workerAddress;
    private List<ProcessorLogField> fields;
    private Long time;
}
