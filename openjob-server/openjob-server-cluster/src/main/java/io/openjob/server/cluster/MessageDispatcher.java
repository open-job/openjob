package io.openjob.server.cluster;

import akka.actor.ActorRef;
import io.openjob.server.cluster.cluster.Cluster;
import io.openjob.server.cluster.message.Join;
import io.openjob.server.common.util.AkkaUtil;
import io.openjob.server.repository.entity.Server;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
@Log4j2
public class MessageDispatcher {
    public void join(Server server) {
        Join join = new Join();
        join.setIp(server.getIp());
        join.setServerId(server.getId());
        join.setAkkaAddress(server.getAkkaAddress());

        Cluster.getNodesMap().forEach((i, s) -> {
            if (Objects.equals(s.getServerId(), server.getId())) {
                AkkaUtil.getClusterActor(s.getAkkaAddress()).tell(join, ActorRef.noSender());
                log.info("join message");
            }
        });
    }
}
