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
        Node joinNode = new Node();
        joinNode.setAkkaAddress(join.getAkkaAddress());
        joinNode.setIp(join.getIp());
        joinNode.setServerId(join.getServerId());

        // Add joinNode.
        ClusterStatus.addNode(join.getServerId(), joinNode);
        log.info("Cluster add joinNode {}({})", join.getAkkaAddress(), join.getServerId());

        if (Objects.isNull(join.getSlotsDTOS())) {
            log.info("Cluster joinNode join message is incomplete(Join slots)");
            return;
        }

        // Join slots info.
        Map<Long, SlotsDTO> slotsMap = join.getSlotsDTOS().stream().collect(Collectors.toMap(SlotsDTO::getServerId, slots -> slots));
        Node currentNode = ClusterStatus.getCurrentNode();
        SlotsDTO slotsDTO = slotsMap.get(currentNode.getServerId());
        if (Objects.isNull(slotsDTO) || Objects.isNull(slotsDTO.getRemoteSlots())) {
            log.error("Cluster joinNode join message is incomplete(Join remove slots)");
            return;
        }

        // Remove cluster status slots.
        ClusterStatus.removeSlots(slotsDTO.getRemoteSlots());
        log.info("Cluster remove slots({})", slotsDTO.getRemoteSlots());

        // Remove timing wheel slots.
    }

    public void receiveNodeFail(NodeFailDTO nodeFailDTO) {
        // Remove node.
        ClusterStatus.removeNode(nodeFailDTO.getServerId());
        log.info("Cluster remove node {}({})", nodeFailDTO.getAkkaAddress(), nodeFailDTO.getServerId());

        if (Objects.isNull(nodeFailDTO.getSlotsDTOS())) {
            log.info("Cluster node fail message is incomplete(Join slots)");
            return;
        }

        // Fail slots.
        Map<Long, SlotsDTO> slotsMap = nodeFailDTO.getSlotsDTOS().stream().collect(Collectors.toMap(SlotsDTO::getServerId, slots -> slots));
        Node currentNode = ClusterStatus.getCurrentNode();
        SlotsDTO slotsDTO = slotsMap.get(currentNode.getServerId());
        if (Objects.isNull(slotsDTO) || Objects.isNull(slotsDTO.getAddSlots())) {
            log.error("Cluster node join message is incomplete(Join remove slots)");
            return;
        }

        // Add cluster status slots.
        ClusterStatus.addSlots(slotsDTO.getAddSlots());
        log.info("Cluster remove slots({})", slotsDTO.getAddSlots());

        // Add timingWheel slots and Add job instance to timingWheel
    }


    public void receiveWorkerJoin(WorkerJoinDTO workerJoinDTO) {
    }

    public void receiveWorkerFail(WorkerFailDTO workerFailDTO) {

    }
}
