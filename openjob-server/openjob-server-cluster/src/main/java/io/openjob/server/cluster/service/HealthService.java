package io.openjob.server.cluster.service;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import io.openjob.common.OpenjobSpringContext;
import io.openjob.common.context.Node;
import io.openjob.common.util.DateUtil;
import io.openjob.common.util.FutureUtil;
import io.openjob.server.cluster.autoconfigure.ClusterProperties;
import io.openjob.server.cluster.dto.NodePingDTO;
import io.openjob.server.cluster.dto.NodePongDTO;
import io.openjob.server.cluster.manager.FailManager;
import io.openjob.server.cluster.manager.JoinManager;
import io.openjob.server.cluster.util.ClusterUtil;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.constant.AkkaConfigConstant;
import io.openjob.server.common.util.ServerUtil;
import io.openjob.server.repository.constant.ServerReportStatusEnum;
import io.openjob.server.repository.dao.JobSlotsDAO;
import io.openjob.server.repository.dao.ServerReportsDAO;
import io.openjob.server.repository.entity.JobSlots;
import io.openjob.server.repository.entity.ServerReports;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Log4j2
@Service
public class HealthService {
    private final FailManager failManager;
    private final ServerReportsDAO serverReportsDAO;
    private final ClusterProperties clusterProperties;
    private final JobSlotsDAO jobSlotsDAO;
    private final JoinManager joinManager;

    private final ActorSystem actorSystem;

    @Autowired
    public HealthService(ServerReportsDAO serverReportsDAO, FailManager failManager, ClusterProperties clusterProperties, JobSlotsDAO jobSlotsDAO, JoinManager joinManager, ActorSystem actorSystem) {
        this.serverReportsDAO = serverReportsDAO;
        this.failManager = failManager;
        this.clusterProperties = clusterProperties;
        this.jobSlotsDAO = jobSlotsDAO;
        this.joinManager = joinManager;
        this.actorSystem = actorSystem;
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

        List<Long> fixedPingList = ClusterUtil.getKnowServers(nodesMap, currentNode, this.clusterProperties.getSpreadSize());
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

            // Current server is unknow.
            if (!nodePongDTO.getKnowServer()) {
                this.checkOnline(node);
            }
        } catch (Exception e) {
            log.info(String.format("Node %s ping failed!", node.toString()), e);

            // Node failed.
            // Record node fail message.
            OpenjobSpringContext.getBean(HealthService.class).checkFailReports(serverId, node);
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
        // Save report
        ServerReports serverReports = new ServerReports();
        serverReports.setServerId(failNode.getServerId());
        serverReports.setReportServerId(failServerId);
        serverReports.setStatus(ServerReportStatusEnum.FAIL.getStatus());
        serverReportsDAO.save(serverReports);

        Long startTime = DateUtil.timestamp() - this.clusterProperties.getNodeFailPeriodTime() / 1000 * 2;
        Long reportsCount = serverReportsDAO.countServerReports(startTime, failServerId, ServerReportStatusEnum.FAIL.getStatus());

        // Offline
        if (reportsCount > this.clusterProperties.getNodeFailTimes()) {
            this.failManager.fail(failNode);
        }
    }

    /**
     * Check online.
     */
    public void checkOnline(Node reportNode) {
        Long currentServerId = ClusterContext.getCurrentNode().getServerId();
        List<JobSlots> jobSlots = this.jobSlotsDAO.listJobSlotsByServerId(currentServerId);

        // Current is online.
        if (!CollectionUtils.isEmpty(jobSlots)) {
            return;
        }

        // Save report
        ServerReports serverReports = new ServerReports();
        serverReports.setServerId(currentServerId);
        serverReports.setReportServerId(reportNode.getServerId());
        serverReports.setStatus(ServerReportStatusEnum.FAIL.getStatus());
        serverReportsDAO.save(serverReports);

        Long startTime = DateUtil.timestamp() - this.clusterProperties.getNodeSuccessPeriodTime() / 1000 * 2;
        Long reportsCount = serverReportsDAO.countServerReports(startTime, currentServerId, ServerReportStatusEnum.FAIL.getStatus());

        // Online
        if (reportsCount > this.clusterProperties.getNodeSuccessTimes()) {
            // Join node to cluster.
            Config config = this.actorSystem.settings().config();
            Integer bindPort = config.getInt(AkkaConfigConstant.AKKA_CANONICAL_PORT);
            String bindHostname = config.getString(AkkaConfigConstant.AKKA_CANONICAL_HOSTNAME);
            this.joinManager.join(bindHostname, bindPort);
        }
    }
}