package io.openjob.server.cluster.message;

import akka.actor.ActorRef;
import io.openjob.server.cluster.dto.NodeFailDTO;
import io.openjob.server.cluster.dto.NodeJoinDTO;
import io.openjob.server.common.util.AkkaUtil;
import io.openjob.server.repository.entity.Server;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
@Log4j2
public class ClusterMessage {
    /**
     * Join message.
     *
     * @param node    node
     * @param servers servers
     */
    public void join(NodeJoinDTO node, List<Server> servers) {
        servers.forEach(s -> {
            if (Objects.equals(s.getId(), node.getServerId())) {
                AkkaUtil.getClusterActor(s.getAkkaAddress()).tell(node, ActorRef.noSender());
            }
        });
    }

    /**
     * Fail message.
     *
     * @param failDTO failDTO
     * @param servers servers
     */
    public void fail(NodeFailDTO failDTO, List<Server> servers) {
        servers.forEach(s -> {
            if (Objects.equals(s.getId(), failDTO.getServerId())) {
                AkkaUtil.getClusterActor(s.getAkkaAddress()).tell(failDTO, ActorRef.noSender());
            }
        });
    }
}
