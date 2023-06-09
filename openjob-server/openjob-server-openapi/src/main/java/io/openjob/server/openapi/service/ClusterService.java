package io.openjob.server.openapi.service;

import io.openjob.server.openapi.request.ClusterOnlineRequest;
import io.openjob.server.openapi.vo.ClusterOnlineVO;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.3
 */
public interface ClusterService {
    ClusterOnlineVO online(ClusterOnlineRequest clusterOnlineRequest);
}
