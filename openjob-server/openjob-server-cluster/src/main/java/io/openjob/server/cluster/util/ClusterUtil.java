package io.openjob.server.cluster.util;

import akka.actor.ActorRef;
import io.openjob.server.common.ClusterContext;
import io.openjob.common.context.Node;
import io.openjob.server.common.constant.ClusterConstant;
import io.openjob.server.common.dto.WorkerDTO;
import io.openjob.server.common.util.ServerUtil;
import io.openjob.server.repository.entity.JobSlots;
import io.openjob.server.repository.entity.Server;
import io.openjob.server.repository.entity.Worker;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
public class ClusterUtil {

    /**
     * Refresh nodes.
     *
     * @param servers servers
     */
    public static void refreshNodes(List<Server> servers) {
        Map<Long, Node> nodes = servers.stream().collect(Collectors.toMap(Server::getId, (s) -> {
            Node node = new Node();
            node.setServerId(s.getId());
            node.setIp(s.getIp());
            node.setAkkaAddress(s.getAkkaAddress());
            return node;
        }));

        ClusterContext.refreshNodeList(nodes);

        log.info(String.format("Refresh servers %s", servers));
    }

    /**
     * Refresh app workers.
     *
     * @param workers refresh workers.
     */
    public static void refreshAppWorkers(List<Worker> workers) {
        Map<Long, List<WorkerDTO>> appWorkers = workers.stream()
                .map(w -> {
                    WorkerDTO workerDTO = new WorkerDTO();
                    workerDTO.setAppId(w.getAppId());
                    workerDTO.setWorkerKey(w.getWorkerKey());
                    workerDTO.setAddress(w.getAddress());
                    workerDTO.setProtocolType(w.getProtocolType());
                    workerDTO.setAppName(w.getAppName());
                    return workerDTO;
                })
                .collect(Collectors.groupingBy(WorkerDTO::getAppId));

        ClusterContext.refreshAppWorkers(appWorkers);
    }

    /**
     * Get know servers.
     *
     * @param nodesMap    nodesMap
     * @param currentNode currentNode
     * @return List
     */
    public static List<Long> getKnowServers(Map<Long, Node> nodesMap, Node currentNode) {
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

    /**
     * Send message
     *
     * @param message     message
     * @param currentNode currentNode
     */
    public static void sendMessage(Object message, Node currentNode) {
        Map<Long, Node> nodesList = ClusterContext.getNodesList();
        List<Long> knowServers = ClusterUtil.getKnowServers(nodesList, currentNode);
        knowServers.forEach(knowId -> ServerUtil.getServerClusterActor(nodesList.get(knowId).getAkkaAddress()).tell(message, ActorRef.noSender()));
    }
}
