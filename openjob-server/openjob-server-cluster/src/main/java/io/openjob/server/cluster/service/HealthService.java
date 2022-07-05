package io.openjob.server.cluster.service;

import io.openjob.common.util.DateUtil;
import io.openjob.server.cluster.ClusterStatus;
import io.openjob.server.cluster.context.Node;
import io.openjob.server.cluster.dto.NodePingDTO;
import io.openjob.server.cluster.dto.ResponseDTO;
import io.openjob.server.cluster.manager.FailManager;
import io.openjob.server.common.SpringContext;
import io.openjob.server.common.constant.ClusterConstant;
import io.openjob.server.common.util.AkkaUtil;
import io.openjob.server.repository.dao.ServerFailReportsDAO;
import io.openjob.server.repository.entity.ServerFailReports;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Service
public class HealthService {
    private final FailManager failManager;
    private final ServerFailReportsDAO serverFailReportsDAO;

    @Autowired
    public HealthService(ServerFailReportsDAO serverFailReportsDAO, FailManager failManager) {
        this.serverFailReportsDAO = serverFailReportsDAO;
        this.failManager = failManager;
    }

    /**
     * Check health.
     */
    public void check() {
        // Ping server list.
        Map<Long, Node> nodesMap = ClusterStatus.getNodesList();
        Node currentNode = ClusterStatus.getCurrentNode();
        List<Long> fixedPingList = this.getFixedPingSeverList(nodesMap, currentNode);

        fixedPingList.forEach(serverId -> doCheck(nodesMap, serverId));
    }

    /**
     * Do check.
     *
     * @param nodesMap nodesMap
     * @param serverId serverId
     */
    public void doCheck(Map<Long, Node> nodesMap, Long serverId) {
        Node node = nodesMap.get(serverId);
        boolean success = false;
        try {
            ResponseDTO responseDTO = (ResponseDTO) AkkaUtil.clusterAsk(node.getAkkaAddress(), new NodePingDTO());
            success = true;
        } catch (Exception e) {
            System.out.println(e);
        }

        if (!success) {
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
     * Get fixed ping server list.
     *
     * @param nodesMap    nodesMap
     * @param currentNode currentNode
     * @return List
     */
    public List<Long> getFixedPingSeverList(Map<Long, Node> nodesMap, Node currentNode) {
        ArrayList<Long> serverIds = new ArrayList<>(nodesMap.keySet());
        Collections.sort(serverIds);
        int serverSize = serverIds.size();
        int currentIndex = serverIds.indexOf(currentNode.getServerId());

        int subSize = serverSize - currentIndex - 1;
        if (subSize > ClusterConstant.CLUSTER_PING_SIZE) {
            subSize = ClusterConstant.CLUSTER_PING_SIZE;
        }

        List<Long> pingList = serverIds.subList(currentIndex, subSize);
        int pingSize = pingList.size();
        int remainPingSize = ClusterConstant.CLUSTER_PING_SIZE - pingSize;
        int needPingSize = remainPingSize;
        if (ClusterConstant.CLUSTER_PING_SIZE > serverSize - 1) {
            needPingSize = serverSize - 1 - remainPingSize;
        }

        if (needPingSize > 0) {
            pingList.addAll(serverIds.subList(0, needPingSize));
        }
        return pingList;
    }
}