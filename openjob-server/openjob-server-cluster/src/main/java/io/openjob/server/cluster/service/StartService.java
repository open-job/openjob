package io.openjob.server.cluster.service;

import com.google.common.collect.Maps;
import io.openjob.common.context.Node;
import io.openjob.server.cluster.dto.NodeJoinDTO;
import io.openjob.server.cluster.message.ClusterMessage;
import io.openjob.server.cluster.util.ClusterStatusUtil;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.repository.constant.ServerStatusEnum;
import io.openjob.server.repository.dao.JobSlotsDAO;
import io.openjob.server.repository.dao.ServerDAO;
import io.openjob.server.repository.entity.JobSlots;
import io.openjob.server.repository.entity.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Service
public class StartService {
    private final ServerDAO serverDAO;
    private final ClusterMessage clusterMessage;
    private final JobSlotsDAO jobSlotsDAO;

    @Autowired
    public StartService(ServerDAO serverDAO, ClusterMessage messageManager, JobSlotsDAO jobSlotsDAO) {
        this.serverDAO = serverDAO;
        this.clusterMessage = messageManager;
        this.jobSlotsDAO = jobSlotsDAO;
    }

    /**
     * Start server
     *
     * @param hostname server hostname.
     * @param port     server port.
     */
    @Transactional(rollbackFor = Exception.class)
    public void start(String hostname, Integer port) {
        // Register server.
        Server currentServer = this.registerOrUpdateServer(hostname, port);

        // Migrate slots.
        List<Server> servers = this.migrateSlots(currentServer);

        // Set current node.
        this.setCurrentNode(currentServer);

        // Refresh nodes.
        ClusterStatusUtil.refreshNodes(servers);

        // Refresh current slots.
        this.refreshCurrentSlots(currentServer);

        // Akka message for join.
        this.sendClusterStartMessage(servers);
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

    private void sendClusterStartMessage(List<Server> servers) {
        Node currentNode = ClusterContext.getCurrentNode();
        NodeJoinDTO nodeJoinDTO = new NodeJoinDTO();
        nodeJoinDTO.setIp(currentNode.getIp());
        nodeJoinDTO.setServerId(currentNode.getServerId());
        nodeJoinDTO.setAkkaAddress(currentNode.getAkkaAddress());
        this.clusterMessage.join(nodeJoinDTO, servers);
    }
}
