package io.openjob.server.cluster.service;

import akka.actor.ActorRef;
import com.google.common.collect.Maps;
import io.openjob.common.util.DateUtil;
import io.openjob.server.cluster.ClusterStatus;
import io.openjob.server.cluster.context.Node;
import io.openjob.server.cluster.dto.NodeFailDTO;
import io.openjob.server.cluster.dto.NodePingDTO;
import io.openjob.server.cluster.dto.ResponseDTO;
import io.openjob.server.cluster.dto.SlotsDTO;
import io.openjob.server.common.SpringContext;
import io.openjob.server.common.constant.ClusterConstant;
import io.openjob.server.common.util.AkkaUtil;
import io.openjob.server.repository.constant.ServerStatusConstant;
import io.openjob.server.repository.dao.ServerDAO;
import io.openjob.server.repository.dao.ServerFailReportsDAO;
import io.openjob.server.repository.dao.JobSlotsDAO;
import io.openjob.server.repository.entity.Server;
import io.openjob.server.repository.entity.ServerFailReports;
import io.openjob.server.repository.entity.JobSlots;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Service
public class HealthService {
    private final ServerDAO serverDAO;
    private final ServerFailReportsDAO serverFailReportsDAO;
    private final JobSlotsDAO jobSlotsDAO;

    @Autowired
    public HealthService(ServerDAO serverDAO, ServerFailReportsDAO serverFailReportsDAO, JobSlotsDAO jobSlotsDAO) {
        this.serverDAO = serverDAO;
        this.serverFailReportsDAO = serverFailReportsDAO;
        this.jobSlotsDAO = jobSlotsDAO;
    }

    public void check() {
        // Ping server list.
        Map<Long, Node> nodesMap = ClusterStatus.getNodesList();
        Node currentNode = ClusterStatus.getCurrentNode();
        List<Long> fixedPingList = this.getFixedPingList(nodesMap, currentNode);

        fixedPingList.forEach(serverId -> doCheck(nodesMap, serverId));
    }

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
            SpringContext.getBean(HealthService.class).checkFailReport(serverId, node);
        }
    }

    @Transactional
    public void checkFailReport(Long failServerId, Node failNode) {
        ServerFailReports serverFailReports = new ServerFailReports();
        serverFailReports.setServerId(failNode.getServerId());
        serverFailReports.setReportServerId(failServerId);
        serverFailReportsDAO.save(serverFailReports);

        Integer startTime = DateUtil.now() - ClusterConstant.CLUSTER_NODE_TIMEOUT / 1000 * 2;
        Long reportsCount = serverFailReportsDAO.countServerFailReports(startTime);
        if (reportsCount > ClusterConstant.CLUSTER_FAIL_TIMES) {
            this.doFail(failNode);
        }
    }

    private Map<Long, List<Long>> computeSlots(Long serverId, List<Server> servers) {
        List<JobSlots> taskSlots = jobSlotsDAO.listJobSlots(serverId);
        int removeSize = (int) Math.floor(taskSlots.size() * (1.0 / servers.size()));

        int index = 0;
        Map<Long, List<Long>> addSlots = Maps.newHashMap();
        for (Server s : servers) {
            if (taskSlots.size() - index < removeSize * 2) {
                removeSize = taskSlots.size() - index;
            }

            List<Long> slotsIds = taskSlots.subList(index, removeSize)
                    .stream()
                    .map(JobSlots::getId)
                    .collect(Collectors.toList());

            addSlots.put(s.getId(), slotsIds);
            index += removeSize;
        }
        return addSlots;
    }

    public void doFail(Node failNode) {
        Long serverId = failNode.getServerId();

        // Update server status (Lock).
        serverDAO.update(serverId, ServerStatusConstant.FAIL.getStatus());

        List<Server> servers = serverDAO.listServers(ServerStatusConstant.OK.getStatus());

        // Refresh current slots.
        Map<Long, List<Long>> addSlots = computeSlots(serverId, servers);
        addSlots.forEach(jobSlotsDAO::updateByIds);

        NodeFailDTO nodeFailDTO = new NodeFailDTO();
        nodeFailDTO.setServerId(failNode.getServerId());
        nodeFailDTO.setAkkaAddress(failNode.getAkkaAddress());
        nodeFailDTO.setIp(failNode.getIp());

        List<SlotsDTO> slotsList = new ArrayList<>();
        servers.forEach(s -> {
            List<Long> slotsIds = addSlots.get(s.getId());
            SlotsDTO slotsDTO = new SlotsDTO();
            slotsDTO.setServerId(s.getId());
            slotsDTO.setAddSlots(slotsIds);
            slotsList.add(slotsDTO);
        });
        nodeFailDTO.setSlotsDTOS(slotsList);

        servers.forEach(s -> {
            if (!Objects.equals(failNode.getServerId(), s.getId())) {
                AkkaUtil.getClusterActor(s.getAkkaAddress()).tell(nodeFailDTO, ActorRef.noSender());
            }
        });
    }

    public List<Long> getFixedPingList(Map<Long, Node> nodesMap, Node currentNode) {
        ArrayList<Long> serverIds = new ArrayList<>(nodesMap.keySet());
        Collections.sort(serverIds);
        int serverSize = serverIds.size();
        int currentIndex = serverIds.indexOf(currentNode.getServerId());
        List<Long> pingList = serverIds.subList(currentIndex, ClusterConstant.CLUSTER_PING_SIZE);
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