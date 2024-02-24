package io.openjob.server.cluster.dto;

import lombok.Data;

import java.util.Set;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.8
 */
@Data
public class WorkerStartRespDTO {
    private Long appId;
    private String appName;
    private Set<String> workerAddressList;
}
