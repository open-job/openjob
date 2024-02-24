package io.openjob.server.openapi.service;

import io.openjob.server.openapi.request.ClusterOnlineRequest;
import io.openjob.server.openapi.vo.ClusterOnlineVO;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.3
 */
public interface OpenClusterService {

    /**
     * online
     *
     * @param clusterOnlineRequest clusterOnlineRequest
     * @return ClusterOnlineVO
     */
    ClusterOnlineVO online(ClusterOnlineRequest clusterOnlineRequest);
}
