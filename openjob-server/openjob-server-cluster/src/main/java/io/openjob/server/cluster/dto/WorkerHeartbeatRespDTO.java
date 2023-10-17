package io.openjob.server.cluster.dto;

import lombok.Data;

import java.util.Set;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.8
 */
@Data
public class WorkerHeartbeatRespDTO {

    /**
     * Worker address list.
     */
    private Set<String> workerAddressList;

    /**
     * Cluster version
     */
    private Long clusterVersion;

    /**
     * Cluster delay version
     */
    private Long clusterDelayVersion;
}
