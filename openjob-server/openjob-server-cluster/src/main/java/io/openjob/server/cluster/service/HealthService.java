package io.openjob.server.cluster.service;

import akka.actor.ActorSelection;
import io.openjob.common.SpringContext;
import io.openjob.common.context.Node;
import io.openjob.common.util.DateUtil;
import io.openjob.common.util.FutureUtil;
import io.openjob.server.cluster.autoconfigure.ClusterProperties;
import io.openjob.server.cluster.dto.NodePingDTO;
import io.openjob.server.cluster.dto.NodePongDTO;
import io.openjob.server.cluster.manager.FailManager;
import io.openjob.server.cluster.util.ClusterUtil;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.constant.ClusterConstant;
import io.openjob.server.common.util.ServerUtil;
import io.openjob.server.repository.dao.ServerFailReportsDAO;
import io.openjob.server.repository.entity.ServerFailReports;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Log4j2
@Service
public class HealthService {
    private final FailManager failManager;
    private final ServerFailReportsDAO serverFailReportsDAO;
    private final ClusterProperties clusterProperties;

    @Autowired
    public HealthService(ServerFailReportsDAO serverFailReportsDAO, FailManager failManager, ClusterProperties clusterProperties) {
        this.serverFailReportsDAO = serverFailReportsDAO;
        this.failManager = failManager;
        this.clusterProperties = clusterProperties;
    }

    /**
     * Check health.
     */
    public void check() {
        // Ping server list.
        Map<Long, Node> nodesMap = ClusterContext.getNodesMap();
        Node currentNode = ClusterContext.getCurrentNode();

        if (Objects.isNull(currentNode)) {
            log.warn("Health check ignore, cluster server is starting.");
            return;
        }

        List<Long> fixedPingList = ClusterUtil.getKnowServers(nodesMap, currentNode);
        fixedPingList.forEach(serverId -> doCheck(nodesMap, serverId));
    }

    /**
     * Do check.
     *
     * @param nodesMap nodesMap
     * @param serverId serverId
     */
    public void doCheck(Map<Long, Node> nodesMap, Long serverId) {
        // Ping info.
        NodePingDTO nodePingDTO = new NodePingDTO();
        nodePingDTO.setClusterVersion(ClusterContext.getSystem().getClusterVersion());
        nodePingDTO.setServerId(ClusterContext.getCurrentNode().getServerId());

        Node node = nodesMap.get(serverId);
        try {
            ActorSelection actor = ServerUtil.getServerClusterActor(node.getAkkaAddress());
            NodePongDTO nodePongDTO = FutureUtil.mustAsk(actor, nodePingDTO, NodePongDTO.class, clusterProperties.getPingTimeout());

            // Node ping success.
            log.info("Ping success version{}", nodePongDTO.getClusterVersion());

            // Current server is unknow.
            if (!nodePongDTO.getKnowServer()) {
                this.checkOnline();
            }
        } catch (Exception e) {
            log.error("Node ping failed!", e);

            // Node failed.
            // Record node fail message.
            SpringContext.getBean(HealthService.class).checkFailReports(serverId, node);
        }
    }

    /**
     * Check fail reports.
     *
     * @param failServerId failServerId
     * @param failNode     failNode
     */
    @Transactional(rollbackFor = Exception.class)
    public void checkFailReports(Long failServerId, Node failNode) {
        ServerFailReports serverFailReports = new ServerFailReports();
        serverFailReports.setServerId(failNode.getServerId());
        serverFailReports.setReportServerId(failServerId);
        serverFailReportsDAO.save(serverFailReports);

        Integer startTime = DateUtil.now() - ClusterConstant.CLUSTER_NODE_TIMEOUT / 1000 * 2;
        Long reportsCount = serverFailReportsDAO.countServerFailReports(startTime);
        if (reportsCount > ClusterConstant.CLUSTER_FAIL_TIMES) {
            this.failManager.fail(failNode);
        }
    }

    /**
     * Check online.
     */
    public void checkOnline() {
    }
}