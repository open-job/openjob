package io.openjob.server.cluster.actor;

import akka.actor.AbstractActor;
import io.openjob.server.cluster.ClusterStatus;
import io.openjob.server.cluster.context.Node;
import io.openjob.server.cluster.dto.NodeJoinDTO;
import io.openjob.server.cluster.dto.NodePingDTO;
import io.openjob.server.cluster.dto.PongDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
@Log4j2
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ClusterActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(NodePingDTO.class, this::receivePing)
                .match(NodeJoinDTO.class, this::receiveJoin)
                .matchAny(obj -> System.out.println("akk mesage tst"))
                .build();
    }

    public void receivePing(NodePingDTO ping) {
        getSender().tell(new PongDTO(), getSelf());
    }

    public void receiveJoin(NodeJoinDTO join) {
        Node node = new Node();
        node.setAkkaAddress(join.getAkkaAddress());
        node.setIp(join.getIp());
        node.setServerId(join.getServerId());

        ClusterStatus.addNode(join.getServerId(), node);

        log.info("join node success!");
    }
}
