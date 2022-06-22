package io.openjob.server.cluster.service;

import io.openjob.server.cluster.ClusterStatus;
import io.openjob.server.cluster.context.Node;
import io.openjob.server.cluster.dto.NodeFailDTO;
import io.openjob.server.cluster.dto.NodeJoinDTO;
import io.openjob.server.cluster.dto.SlotsDTO;
import io.openjob.server.cluster.dto.WorkerFailDTO;
import io.openjob.server.cluster.dto.WorkerJoinDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Log4j2
@Service
public class ClusterService {

    /**
     * Receive node join message.
     */
    public void receiveNodeJoin(NodeJoinDTO join) {
        Node node = new Node();
        node.setAkkaAddress(join.getAkkaAddress());
        node.setIp(join.getIp());
        node.setServerId(join.getServerId());

        // Add node.
        ClusterStatus.addNode(join.getServerId(), node);
        log.info("Cluster add node {}({})", join.getAkkaAddress(), join.getServerId());

        if (Objects.isNull(join.getSlotsDTOS())) {
            log.info("Cluster node join message is incomplete(Join slots)");
            return;
        }

        // Join slots info.
        Map<Long, SlotsDTO> slotsMap = join.getSlotsDTOS().stream().collect(Collectors.toMap(SlotsDTO::getServerId, slots -> slots));
        Node currentNode = ClusterStatus.getCurrentNode();
        SlotsDTO slotsDTO = slotsMap.get(currentNode.getServerId());
        if (Objects.isNull(slotsDTO) || Objects.isNull(slotsDTO.getRemoteSlots())) {
            log.error("Cluster node join message is incomplete(Join remove slots)");
            return;
        }

        // Remove cluster status slots.
        ClusterStatus.removeSlots(currentNode.getServerId(), slotsDTO.getRemoteSlots());
        log.info("Cluster remove slots({})", slotsDTO.getRemoteSlots());

        // Remove timing wheel slots.
    }

    public void receiveNodeFail(NodeFailDTO nodeFailDTO) {
        ClusterStatus.remote(nodeFailDTO.getServerId());
    }


    public void receiveWorkerJoin(WorkerJoinDTO workerJoinDTO) {
    }

    public void receiveWorkerFail(WorkerFailDTO workerFailDTO) {

    }
}
