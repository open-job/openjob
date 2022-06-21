package io.openjob.server.cluster.actor;

import akka.actor.AbstractActor;
import io.openjob.server.cluster.dto.NodeFailDTO;
import io.openjob.server.cluster.dto.NodeJoinDTO;
import io.openjob.server.cluster.dto.NodePingDTO;
import io.openjob.server.cluster.dto.PongDTO;
import io.openjob.server.cluster.dto.WorkerFailDTO;
import io.openjob.server.cluster.dto.WorkerJoinDTO;
import io.openjob.server.cluster.service.NodeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final NodeService nodeService;

    @Autowired
    public ClusterActor(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(NodePingDTO.class, this::receivePing)
                .match(NodeJoinDTO.class, nodeService::receiveNodeJoin)
                .match(NodeFailDTO.class, nodeService::receiveNodeFail)
                .match(WorkerJoinDTO.class, nodeService::receiveWorkerJoin)
                .match(WorkerFailDTO.class, nodeService::receiveWorkerFail)
                .matchAny(obj -> System.out.println("akk mesage tst"))
                .build();
    }


    public void receivePing(NodePingDTO nodePingDTO) {
        getSender().tell(new PongDTO(), getSelf());
    }
}
