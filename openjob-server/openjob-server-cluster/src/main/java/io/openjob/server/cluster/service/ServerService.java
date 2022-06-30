package io.openjob.server.cluster.service;

import com.google.common.collect.Maps;
import io.openjob.server.cluster.ClusterStatus;
import io.openjob.server.cluster.context.Node;
import io.openjob.server.cluster.message.ClusterMessage;
import io.openjob.server.repository.constant.ServerStatusConstant;
import io.openjob.server.repository.dao.JobSlotsDAO;
import io.openjob.server.repository.dao.ServerDAO;
import io.openjob.server.repository.entity.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Service
public class ServerService {
    private final ServerDAO serverDAO;
    private final ClusterMessage ClusterMessage;
    private final JobSlotsDAO jobSlotsDAO;

    @Autowired
    public ServerService(ServerDAO serverDAO, ClusterMessage messageManager, JobSlotsDAO jobSlotsDAO) {
        this.serverDAO = serverDAO;
        this.ClusterMessage = messageManager;
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
        Server currentServer = this.registerServer(hostname, port);

        // Job slots for update to lock.
        List<Long> migratedSlots = this.migratedSlotsForJoin();

        // Migrate slots.
        this.migrateSlots(currentServer, migratedSlots);

        // Set current node.
        this.SetCurrentNode(currentServer);

        // Refresh nodes.
        this.refreshNodes();

        // Refresh slots.

        // Akka message for join.
        this.ClusterMessage.join(currentServer);
    }

    /**
     * Migrate Slots to current server.
     *
     * @param currentServer server
     * @param slots         slots
     */
    private void migrateSlots(Server currentServer, List<Long> slots) {
        // First server node.
        if (slots.isEmpty()) {
            jobSlotsDAO.updateByServerId(currentServer.getId());
            return;
        }

        jobSlotsDAO.updateByServerId(currentServer.getId(), slots);
    }

    /**
     * Register server.
     * If server is exists to update.
     *
     * @param hostname server hostname.
     * @param port     server port.
     * @return Server
     */
    private Server registerServer(String hostname, Integer port) {
        // Has been registered
        String akkaAddress = String.format("%s:%d", hostname, port);
        Optional<Server> optionalServer = serverDAO.getOne(akkaAddress);
        if (optionalServer.isPresent()) {
            // Update status
            Server server = optionalServer.get();
            Long id = server.getId();
            serverDAO.update(id, ServerStatusConstant.OK.getStatus());
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
    private void SetCurrentNode(Server server) {
        Node currentNode = new Node();
        currentNode.setServerId(server.getId());
        currentNode.setIp(server.getIp());
        currentNode.setAkkaAddress(server.getAkkaAddress());
        ClusterStatus.setCurrentNode(currentNode);
    }

    /**
     * Refresh nodes.
     */
    private void refreshNodes() {
        List<Server> servers = serverDAO.listServers(ServerStatusConstant.OK.getStatus());
        Map<Long, Node> nodes = new HashMap<>();
        servers.forEach(s -> {
            Node node = new Node();
            node.setServerId(s.getId());
            node.setIp(s.getIp());
            node.setAkkaAddress(s.getAkkaAddress());
            nodes.put(s.getId(), node);
        });

        ClusterStatus.refreshNodeList(nodes);
    }

    /**
     * Calculate remove slots.
     *
     * @return slots map.
     */
    private List<Long> migratedSlotsForJoin() {
        Map<Long, List<Long>> serverIdToSlots = Maps.newHashMap();
        jobSlotsDAO.listJobSlotsForUpdate().forEach(taskSlots -> {
            if (taskSlots.getServerId() > 0) {
                List<Long> slots = serverIdToSlots.computeIfAbsent(taskSlots.getServerId(), m -> new ArrayList<>());
                slots.add(taskSlots.getId());
                serverIdToSlots.put(taskSlots.getServerId(), slots);
            }
        });

        // Remove server slots.
        int slotsServerCount = serverIdToSlots.size();
        List<Long> migratedList = new ArrayList<>();
        serverIdToSlots.forEach((id, slots) -> {
            int migratedSize = (int) Math.floor(slots.size() * (1.0 / (slotsServerCount + 1)));
            List<Long> migratedIds = slots.subList(0, migratedSize - 1);
            migratedList.addAll(migratedIds);
        });

        return migratedList;
    }
}
