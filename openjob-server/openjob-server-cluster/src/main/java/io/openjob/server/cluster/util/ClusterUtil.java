package io.openjob.server.cluster.util;

import akka.actor.ActorRef;
import io.openjob.server.common.ClusterContext;
import io.openjob.common.context.Node;
import io.openjob.server.common.dto.WorkerDTO;
import io.openjob.server.common.util.ServerUtil;
import io.openjob.server.repository.entity.JobSlots;
import io.openjob.server.repository.entity.Server;
import io.openjob.server.repository.entity.Worker;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
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
     * Refresh slot map.
     *
     * @param jobSlots slots
     */
    public static void refreshSlotsListMap(List<JobSlots> jobSlots) {
        ClusterContext.refreshSlotsListMap(jobSlots.stream().collect(Collectors.toMap(JobSlots::getId, JobSlots::getServerId)));
    }

    /**
     * Send message
     *
     * @param message message
     * @param servers server list.
     */
    public static void sendMessage(Object message, List<Server> servers) {
        servers.forEach(s -> {
            if (!Objects.equals(s.getId(), ClusterContext.getCurrentNode().getServerId())) {
                ServerUtil.getServerClusterActor(s.getAkkaAddress()).tell(message, ActorRef.noSender());
            }
        });
    }
}
