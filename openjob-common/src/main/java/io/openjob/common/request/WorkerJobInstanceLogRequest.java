package io.openjob.common.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class WorkerJobInstanceLogRequest implements Serializable {
    private String workerAddress;
    private List<WorkerJobInstanceLogMessageRequest> messageList;
}
