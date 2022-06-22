package io.openjob.server.cluster.actor;

import akka.actor.AbstractActor;
import io.openjob.server.cluster.dto.NodeFailDTO;
import io.openjob.server.cluster.dto.NodeJoinDTO;
import io.openjob.server.cluster.dto.NodePingDTO;
import io.openjob.server.cluster.dto.PongDTO;
import io.openjob.server.cluster.dto.WorkerFailDTO;
import io.openjob.server.cluster.dto.WorkerJoinDTO;
import io.openjob.server.cluster.service.ClusterService;
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
    private final ClusterService clusterService;

    @Autowired
    public ClusterActor(ClusterService nodeService) {
        this.clusterService = nodeService;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(NodePingDTO.class, this::receivePing)
                .match(NodeJoinDTO.class, clusterService::receiveNodeJoin)
                .match(NodeFailDTO.class, clusterService::receiveNodeFail)
                .match(WorkerJoinDTO.class, clusterService::receiveWorkerJoin)
                .match(WorkerFailDTO.class, clusterService::receiveWorkerFail)
                .matchAny(obj -> System.out.println("akk mesage tst"))
                .build();
    }


    public void receivePing(NodePingDTO nodePingDTO) {
        getSender().tell(new PongDTO(), getSelf());
    }
}
