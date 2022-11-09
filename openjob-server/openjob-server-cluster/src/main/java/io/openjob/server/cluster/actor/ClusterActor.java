package io.openjob.server.cluster.actor;

import akka.actor.AbstractActor;
import io.openjob.common.response.Result;
import io.openjob.server.cluster.dto.NodeFailDTO;
import io.openjob.server.cluster.dto.NodeJoinDTO;
import io.openjob.server.cluster.dto.NodePingDTO;
import io.openjob.server.cluster.dto.NodePongDTO;
import io.openjob.server.cluster.service.ClusterService;
import io.openjob.server.common.ClusterContext;
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
                .match(NodePingDTO.class, this::handleNodePing)
                .match(NodeJoinDTO.class, this::handleNodeJoin)
                .match(NodeFailDTO.class, this::handleNodeFail)
                .matchAny(obj -> System.out.println("akk mesage tst"))
                .build();
    }

    /**
     * Handle node ping.
     *
     * @param nodePingDTO nodePing
     */
    public void handleNodePing(NodePingDTO nodePingDTO) {
        // Do node ping.
        this.clusterService.receiveNodePing(nodePingDTO);

        // Response pong.
        NodePongDTO nodePongDTO = new NodePongDTO();
        nodePongDTO.setClusterVersion(ClusterContext.getSystem().getClusterVersion());

        // Response know status.
        boolean isKnowServer = ClusterContext.getNodesMap().containsKey(nodePingDTO.getServerId());
        nodePongDTO.setKnowServer(isKnowServer);
        getSender().tell(Result.success(nodePongDTO), getSelf());
    }

    /**
     * Handle node join.
     *
     * @param nodeJoinDTO nodeJoin
     */
    public void handleNodeJoin(NodeJoinDTO nodeJoinDTO) {
        this.clusterService.receiveNodeJoin(nodeJoinDTO);
    }

    /**
     * Handle node fail.
     *
     * @param nodeFailDTO nodeFail
     */
    public void handleNodeFail(NodeFailDTO nodeFailDTO) {
        this.clusterService.receiveNodeFail(nodeFailDTO);
    }
}
