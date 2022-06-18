package io.openjob.server.cluster.service;

import akka.actor.ActorRef;
import io.openjob.common.util.DateUtil;
import io.openjob.server.cluster.cluster.Cluster;
import io.openjob.server.cluster.cluster.Node;
import io.openjob.server.cluster.message.Fail;
import io.openjob.server.cluster.message.Ping;
import io.openjob.server.common.constant.ClusterConstant;
import io.openjob.server.common.util.AkkaUtil;
import io.openjob.server.repository.dao.ServerDAO;
import io.openjob.server.repository.dao.ServerFailReportsDAO;
import io.openjob.server.repository.entity.ServerFailReports;
import org.springframework.beans.factory.annotation.Autowired;
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
public class HealthService {
    private final ServerDAO serverDAO;
    private final ServerFailReportsDAO serverFailReportsDAO;

    @Autowired
    public HealthService(ServerDAO serverDAO, ServerFailReportsDAO serverFailReportsDAO) {
        this.serverDAO = serverDAO;
        this.serverFailReportsDAO = serverFailReportsDAO;
    }

    public void check() {
        Map<Long, Node> nodesMap = Cluster.getNodesMap();
        Integer nodeCount = nodesMap.size();
        List<Long> serverIds = new ArrayList<>();
        Long currentServerId = Cluster.getCurrentNode().getServerId();
        nodesMap.forEach((id, n) -> {
            if (currentServerId != id) {
                serverIds.add(id);
            }
        });

        serverIds.forEach(serverId -> {
            Node node = nodesMap.get(serverId);
            boolean success = false;
            try {
                AkkaUtil.clusterAsk(node.getAkkaAddress(), new Ping());
                success = true;
            } catch (Exception e) {

            }

            if (!success) {
                ServerFailReports serverFailReports = new ServerFailReports();
                serverFailReports.setServerId(node.getServerId());
                serverFailReports.setReportServerId(serverId);

                // Update server status

                // Reset slots

                Integer startTime = DateUtil.now() - ClusterConstant.CLUSTER_NODE_TIMEOUT / 1000 * 2;
                Long reportsCount = serverFailReportsDAO.countServerFailReports(startTime);
                if (reportsCount > 1) {
                    this.fail(node);
                }
            }

        });
    }

    private void fail(Node failNode) {
        Cluster.getNodesMap().forEach(((id, node) -> {
            if (!Objects.equals(failNode.getServerId(), node.getServerId())) {
                AkkaUtil.getClusterActor(node.getAkkaAddress()).tell(new Fail(), ActorRef.noSender());
            }
        }));
    }
}
