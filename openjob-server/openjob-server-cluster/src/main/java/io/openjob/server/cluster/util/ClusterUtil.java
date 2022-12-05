package io.openjob.server.cluster.util;

import akka.actor.ActorRef;
import io.openjob.common.context.Node;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.dto.WorkerDTO;
import io.openjob.server.common.util.ServerUtil;
import io.openjob.server.repository.entity.Server;
import io.openjob.server.repository.entity.Worker;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
     * @param spreadSize  spreadSize
     * @return List
     */
    public static List<Long> getKnowServers(Map<Long, Node> nodesMap, Node currentNode, Integer spreadSize) {
        ArrayList<Long> serverIds = new ArrayList<>(nodesMap.keySet());
        Collections.sort(serverIds);
        int serverSize = serverIds.size();

        // Server size is less than spread size.
        if (serverSize <= spreadSize) {
            return serverIds.stream().filter(c -> !c.equals(currentNode.getServerId())).collect(Collectors.toList());
        }

        // Current index and sub size from tail
        int currentIndex = serverIds.indexOf(currentNode.getServerId());
        int subSize = serverSize - currentIndex - 1;
        if (subSize > spreadSize) {
            subSize = spreadSize;
        }

        // Sub from tail by size.
        List<Long> fixedList = new ArrayList<>(serverIds.subList(currentIndex + 1, currentIndex + 1 + subSize));
        int pingSize = fixedList.size();
        int needPingSize = spreadSize - pingSize;

        // Sub from head by size.
        if (needPingSize > 0) {
            fixedList.addAll(serverIds.subList(0, needPingSize));
        }

        Collections.sort(fixedList);
        return fixedList;
    }

    /**
     * Send message
     *
     * @param message     message
     * @param currentNode currentNode
     * @param spreadSize  spreadSize
     */
    public static void sendMessage(Object message, Node currentNode, Integer spreadSize, Set<Long> excludeNodes) {
        Map<Long, Node> nodesList = ClusterContext.getNodesMap();
        List<Long> knowServers = ClusterUtil.getKnowServers(nodesList, currentNode, spreadSize);
        knowServers.forEach(knowId -> {
            try {
                ServerUtil.getServerClusterActor(nodesList.get(knowId).getAkkaAddress()).tell(message, ActorRef.noSender());
            } catch (Throwable e) {
                log.warn("Akka cluster message error!", e);
            }
        });
    }
}
