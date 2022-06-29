package io.openjob.server.cluster.message;

import akka.actor.ActorRef;
import io.openjob.server.cluster.ClusterStatus;
import io.openjob.server.cluster.dto.NodeJoinDTO;
import io.openjob.server.cluster.dto.SlotsDTO;
import io.openjob.server.common.util.AkkaUtil;
import io.openjob.server.repository.entity.Server;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
@Log4j2
public class ClusterMessage {
    public void join(Server currentServer) {
        NodeJoinDTO join = new NodeJoinDTO();
        join.setIp(currentServer.getIp());
        join.setServerId(currentServer.getId());
        join.setAkkaAddress(currentServer.getAkkaAddress());

        ClusterStatus.getNodesList().forEach((i, s) -> {
            if (Objects.equals(s.getServerId(), currentServer.getId())) {
                AkkaUtil.getClusterActor(s.getAkkaAddress()).tell(join, ActorRef.noSender());
            }
        });
    }
}
