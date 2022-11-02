package io.openjob.server.cluster.service;

import com.google.common.collect.Maps;
import io.openjob.common.context.Node;
import io.openjob.common.util.DateUtil;
import io.openjob.server.cluster.dto.NodeFailDTO;
import io.openjob.server.cluster.dto.NodeJoinDTO;
import io.openjob.server.cluster.dto.NodePingDTO;
import io.openjob.server.cluster.util.ClusterUtil;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.dto.SystemDTO;
import io.openjob.server.repository.constant.ServerStatusEnum;
import io.openjob.server.repository.dao.JobSlotsDAO;
import io.openjob.server.repository.dao.ServerDAO;
import io.openjob.server.repository.dao.SystemDAO;
import io.openjob.server.repository.entity.JobSlots;
import io.openjob.server.repository.entity.Server;
import io.openjob.server.repository.entity.System;
import io.openjob.server.scheduler.wheel.WheelManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Log4j2
@Service
public class ClusterService {
    private final ServerDAO serverDAO;
    private final JobSlotsDAO jobSlotsDAO;

    private final WheelManager wheelManager;

    private final SystemDAO systemDAO;

    /**
     * Refresh status.
     */
    private final AtomicBoolean refreshing = new AtomicBoolean(false);

    @Autowired
    public ClusterService(ServerDAO serverDAO, JobSlotsDAO jobSlotsDAO, WheelManager wheelManager, SystemDAO systemDAO) {
        this.serverDAO = serverDAO;
        this.jobSlotsDAO = jobSlotsDAO;
        this.wheelManager = wheelManager;
        this.systemDAO = systemDAO;
    }

    /**
     * Cluster node start.
     *
     * @param hostname hostname
     * @param port     port
     */
    @Transactional(rollbackFor = Exception.class)
    public void start(String hostname, Integer port) {
        // Register server.
        Server currentServer = this.registerOrUpdateServer(hostname, port);

        // Migrate slots.
        List<Server> servers = this.migrateSlots(currentServer);

        // Set current node.
        this.setCurrentNode(currentServer);

        // Refresh system.
        this.systemDAO.updateClusterVersion(DateUtil.timestamp());
        this.refreshSystem();

        // Refresh nodes.
        ClusterUtil.refreshNodes(servers);

        // Refresh current slots.
        this.refreshCurrentSlots(currentServer);

        // Akka message for join.
        this.sendClusterStartMessage();
    }


    /**
     * Receive node ping.
     *
     * @param nodePingDTO nodePingDTO
     */
    public void receiveNodePing(NodePingDTO nodePingDTO) {
        Long clusterVersion = ClusterContext.getSystem().getClusterVersion();
        if (clusterVersion >= nodePingDTO.getClusterVersion()) {
            return;
        }

        if (this.refreshing.compareAndSet(false, true)) {
            return;
        }

        log.info("Begin to refreshing");
    }

    /**
     * Receive node join message.
     *
     * @param join join
     */
    public void receiveNodeJoin(NodeJoinDTO join) {
        if (this.refreshing.compareAndSet(false, true)) {
            return;
        }

        log.info("Join node starting {}({})", join.getAkkaAddress(), join.getServerId());

        // Refresh nodes.
        this.refreshNodes();

        // Refresh slots.
        Set<Long> removeSlots = this.refreshSlotsByReceive(true);

        // Remove job instance from timing wheel.
        this.wheelManager.removeBySlotsId(removeSlots);

        // Refresh system.
        this.refreshSystem();

        this.refreshing.set(false);
        log.info("Join node success {}({})", join.getAkkaAddress(), join.getServerId());
    }

    /**
     * Receive node fail message.
     *
     * @param fail fail
     */
    public void receiveNodeFail(NodeFailDTO fail) {
        if (this.refreshing.compareAndSet(false, true)) {
            return;
        }

        log.info("Fail node starting {}({})", fail.getAkkaAddress(), fail.getServerId());

        // Refresh nodes.
        this.refreshNodes();

        // Refresh slots.
        Set<Long> addSlots = this.refreshSlotsByReceive(false);

        // Refresh system.
        this.refreshSystem();
        this.refreshing.set(false);

        log.info("Add new slots{}", addSlots);
        // Add job instance to timing wheel.
        log.info("Fail node starting {}({})", fail.getAkkaAddress(), fail.getServerId());
    }

    /**
     * Refresh nodes.
     */
    private void refreshNodes() {
        List<Server> servers = serverDAO.listServers(ServerStatusEnum.OK.getStatus());
        ClusterUtil.refreshNodes(servers);
        log.info("Refresh nodes {}", servers);
    }

    /**
     * Refresh current slots.
     *
     * @param currentServer currentServer
     */
    private void refreshCurrentSlots(Server currentServer) {
        List<JobSlots> jobSlots = jobSlotsDAO.listJobSlotsByServerId(currentServer.getId());
        Set<Long> currentSlots = jobSlots.stream().map(JobSlots::getId).collect(Collectors.toSet());
        ClusterContext.refreshCurrentSlots(currentSlots);

        log.info(String.format("Refresh slots %s", currentSlots));
    }

    /**
     * Refresh job slots.
     *
     * @param isJoin isJoin
     * @return Set
     */
    private Set<Long> refreshSlotsByReceive(Boolean isJoin) {
        Node currentNode = ClusterContext.getCurrentNode();
        Set<Long> currentSlots = ClusterContext.getCurrentSlots();
        List<JobSlots> jobSlots = jobSlotsDAO.listJobSlotsByServerId(currentNode.getServerId());
        Set<Long> newSlots = jobSlots.stream().map(JobSlots::getId).collect(Collectors.toSet());

        // Refresh current slots.
        ClusterContext.refreshCurrentSlots(newSlots);

        log.info("Refresh slots {}", jobSlots);
        ClusterUtil.refreshSlotsListMap(jobSlots);

        if (isJoin) {
            // Node remove slots.
            currentSlots.removeAll(newSlots);
            return currentSlots;
        } else {
            // Node add slots.
            newSlots.removeAll(currentSlots);
            return newSlots;
        }
    }

    /**
     * Register server.
     * If server is exists to update.
     *
     * @param hostname server hostname.
     * @param port     server port.
     * @return Server
     */
    private Server registerOrUpdateServer(String hostname, Integer port) {
        // Has been registered
        String akkaAddress = String.format("%s:%d", hostname, port);
        Optional<Server> optionalServer = serverDAO.getOne(akkaAddress);
        if (optionalServer.isPresent()) {
            // Update status
            Server server = optionalServer.get();
            Long id = server.getId();
            serverDAO.update(id, ServerStatusEnum.OK.getStatus());
            return server;
        }

        // First register server
        Server server = new Server();
        server.setIp(hostname);
        server.setAkkaAddress(akkaAddress);
        Long id = serverDAO.save(server);
        server.setId(id);
        return server;
    }

    /**
     * Set current node.
     *
     * @param server server
     */
    private void setCurrentNode(Server server) {
        Node currentNode = new Node();
        currentNode.setServerId(server.getId());
        currentNode.setIp(server.getIp());
        currentNode.setAkkaAddress(server.getAkkaAddress());
        ClusterContext.setCurrentNode(currentNode);

        log.info(String.format("Current node %s", server));
    }

    /**
     * Refresh system.
     */
    private void refreshSystem() {
        System system = this.systemDAO.getOne();
        SystemDTO systemDTO = new SystemDTO();
        systemDTO.setMaxSlot(system.getMaxSlot());
        systemDTO.setClusterVersion(system.getClusterVersion());
        systemDTO.setClusterSupervisorSlot(system.getClusterSupervisorSlot());
        systemDTO.setWorkerSupervisorSlot(system.getWorkerSupervisorSlot());

        ClusterContext.refreshSystem(systemDTO);

        log.info(String.format("Refresh %s", system));
    }

    /**
     * Calculate remove slots.
     *
     * @return slots map.
     */
    private List<Server> migrateSlots(Server currentServer) {
        Map<Long, List<Long>> serverIdToSlots = Maps.newHashMap();
        jobSlotsDAO.listJobSlots().forEach(taskSlots -> {
            if (taskSlots.getServerId() > 0) {
                List<Long> slots = serverIdToSlots.computeIfAbsent(taskSlots.getServerId(), m -> new ArrayList<>());
                slots.add(taskSlots.getId());
                serverIdToSlots.put(taskSlots.getServerId(), slots);
            }
        });

        // Remove server slots.
        List<Server> servers = serverDAO.listServers(ServerStatusEnum.OK.getStatus());
        int slotsServerCount = servers.size() - 1;
        List<Long> migratedList = new ArrayList<>();
        serverIdToSlots.forEach((id, slots) -> {
            int migratedSize = (int) Math.floor(slots.size() * (1.0 / (slotsServerCount + 1)));
            List<Long> migratedIds = slots.subList(0, migratedSize - 1);
            migratedList.addAll(migratedIds);
        });


        // First server node.
        if (migratedList.isEmpty()) {
            jobSlotsDAO.updateByServerId(currentServer.getId());
            return servers;
        }

        jobSlotsDAO.updateByServerId(currentServer.getId(), migratedList);
        return servers;
    }

    /**
     * Send start message.
     */
    private void sendClusterStartMessage() {
        Node currentNode = ClusterContext.getCurrentNode();
        NodeJoinDTO nodeJoinDTO = new NodeJoinDTO();
        nodeJoinDTO.setIp(currentNode.getIp());
        nodeJoinDTO.setServerId(currentNode.getServerId());
        nodeJoinDTO.setAkkaAddress(currentNode.getAkkaAddress());

        ClusterUtil.sendMessage(nodeJoinDTO, currentNode);
    }
}
