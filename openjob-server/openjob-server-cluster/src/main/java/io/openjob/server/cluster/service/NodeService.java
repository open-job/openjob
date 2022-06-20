package io.openjob.server.cluster.service;

import io.openjob.server.cluster.ClusterStatus;
import io.openjob.server.cluster.context.Node;
import io.openjob.server.cluster.dto.NodeFailDTO;
import io.openjob.server.cluster.dto.NodeJoinDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Log4j2
@Service
public class NodeService {

    /**
     * Receive node join message.
     */
    public void receiveNodeJoin(NodeJoinDTO join) {
        Node node = new Node();
        node.setAkkaAddress(join.getAkkaAddress());
        node.setIp(join.getIp());
        node.setServerId(join.getServerId());

        ClusterStatus.appendNode(join.getServerId(), node);

        log.info("join node success!");
    }


    public void receiveNodeJoin(NodeFailDTO nodeFailDTO) {
        log.info("join node success!");
    }
}
