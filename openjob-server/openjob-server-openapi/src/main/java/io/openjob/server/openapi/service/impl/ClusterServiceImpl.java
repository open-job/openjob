package io.openjob.server.openapi.service.impl;

import io.openjob.common.context.Node;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.openapi.request.ClusterOnlineRequest;
import io.openjob.server.openapi.service.ClusterService;
import io.openjob.server.openapi.vo.ClusterOnlineVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.3
 */
@Service
public class ClusterServiceImpl implements ClusterService {
    @Override
    public ClusterOnlineVO online(ClusterOnlineRequest clusterOnlineRequest) {
        ClusterOnlineVO clusterOnlineVO = new ClusterOnlineVO();
        List<String> servers = ClusterContext.getNodesMap().values().stream()
                .map(Node::getAkkaAddress)
                .collect(Collectors.toList());
        clusterOnlineVO.setServers(servers);
        return clusterOnlineVO;
    }
}
