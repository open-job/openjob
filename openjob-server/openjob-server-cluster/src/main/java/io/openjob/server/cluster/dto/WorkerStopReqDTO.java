package io.openjob.server.cluster.dto;

import lombok.Data;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.8
 */
@Data
public class WorkerStopReqDTO {
    private String workerKey;
    private String appName;
    private String address;
}
