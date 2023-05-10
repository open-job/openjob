package io.openjob.worker.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class MasterBatchStartContainerRequest implements Serializable {
    private Long jobInstanceId;
    private Long jobId;
    private List<MasterStartContainerRequest> startContainerRequests;
}
