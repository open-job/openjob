package io.openjob.server.openapi.manager;

import com.google.common.collect.Maps;
import io.openjob.common.OpenjobSpringContext;
import io.openjob.common.context.Node;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.openapi.autoconfigure.ClusterProperties;
import io.openjob.server.openapi.dto.NodeJoinDTO;
import io.openjob.server.openapi.util.ClusterUtil;
import io.openjob.server.repository.constant.ServerStatusEnum;
import io.openjob.server.repository.dao.JobSlotsDAO;
import io.openjob.server.repository.dao.ServerDAO;
import io.openjob.server.repository.entity.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
@Component
public class JoinManager {
    private final ServerDAO serverDAO;
    private final JobSlotsDAO jobSlotsDAO;
    private final RefreshManager refreshManager;
    private final ClusterProperties clusterProperties;

    /**
     * Refresh status.
     */
    @Autowired
    public JoinManager(ServerDAO serverDAO, JobSlotsDAO jobSlotsDAO, RefreshManager refreshManager, ClusterProperties clusterProperties) {
        this.serverDAO = serverDAO;
        this.jobSlotsDAO = jobSlotsDAO;
        this.refreshManager = refreshManager;
        this.clusterProperties = clusterProperties;
    }

    /**
     * Cluster node start.
     *
     * @param hostname hostname
     * @param port     port
     */
    public void join(String hostname, Integer port) {
        try {
            // Do node join.
            boolean result = ClusterUtil.clusterNodeOperate(
                    this.clusterProperties.getSpreadRetryTimes(),
                    () -> OpenjobSpringContext.getBean(this.getClass()).doJoin(hostname, port));

            // Success to send cluster message.
            if (result) {
                // Akka message for join.
                this.sendClusterStartMessage();
            }
        } catch (InterruptedException interruptedException) {
            log.info("Node fail error!", interruptedException);
        }
    }

    /**
     * Cluster node  do join.
     *
     * @param hostname hostname
     * @param port     port
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean doJoin(String hostname, Integer port) {
        // Refresh system.
        // Lock system cluster version.
        this.refreshManager.refreshSystem(true);

        // Register server.
        Server currentServer = this.registerOrUpdateServer(hostname, port);

        // Migrate slots.
        List<Server> servers = this.migrateSlots(currentServer);

        // Set current node.
        this.setCurrentNode(currentServer);

        // Refresh nodes.
        ClusterUtil.refreshNodes(servers);

        // Refresh current slots.
        this.refreshManager.refreshCurrentSlots();

        // Refresh app workers;
        this.refreshManager.refreshAppWorkers();
        return true;
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
            List<Long> migratedIds = slots.subList(0, migratedSize);
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
     * Set current node.
     *
     * @param server server
     */
    private void setCurrentNode(Server server) {
        Node currentNode = new Node();
        currentNode.setServerId(server.getId());
        currentNode.setIp(server.getIp());
        currentNode.setAkkaAddress(server.getAkkaAddress());
        currentNode.setStatus(server.getStatus());
        ClusterContext.setCurrentNode(currentNode);

        log.info(String.format("Current node %s", server));
    }

    /**
     * Send start message.
     */
    private void sendClusterStartMessage() {
        Node currentNode = ClusterContext.getCurrentNode();
        NodeJoinDTO nodeJoinDTO = new NodeJoinDTO();
        nodeJoinDTO.setIp(currentNode.getIp());
        nodeJoinDTO.setClusterVersion(ClusterContext.getSystem().getClusterVersion());
        nodeJoinDTO.setServerId(currentNode.getServerId());
        nodeJoinDTO.setAkkaAddress(currentNode.getAkkaAddress());

        ClusterUtil.sendMessage(nodeJoinDTO, currentNode, this.clusterProperties.getSpreadSize(), new HashSet<>());
    }
}
