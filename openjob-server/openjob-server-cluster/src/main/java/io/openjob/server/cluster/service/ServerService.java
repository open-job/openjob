package io.openjob.server.cluster.service;

import com.google.common.collect.Maps;
import io.openjob.server.cluster.ClusterStatus;
import io.openjob.server.cluster.context.Node;
import io.openjob.server.cluster.message.ClusterMessage;
import io.openjob.server.common.SpringContext;
import io.openjob.server.repository.constant.ServerStatusConstant;
import io.openjob.server.repository.dao.ServerDAO;
import io.openjob.server.repository.dao.TaskSlotsDAO;
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
    private final TaskSlotsDAO taskSlotsDAO;

    @Autowired
    public ServerService(ServerDAO serverDAO, ClusterMessage messageManager, TaskSlotsDAO taskSlotsDAO) {
        this.serverDAO = serverDAO;
        this.ClusterMessage = messageManager;
        this.taskSlotsDAO = taskSlotsDAO;
    }

    public void join(String hostname, Integer port) {
        Map<Long, List<Long>> removeSlots = this.computeSlots();
        SpringContext.getBean(ServerService.class).doJoin(hostname, port, removeSlots);
    }

    @Transactional
    public void doJoin(String hostname, Integer port, Map<Long, List<Long>> removeSlots) {
        // Register server.
        Server currentServer = this.registerServer(hostname, port);

        // Refresh nodes.
        this.refreshNodes(currentServer);

        // Update slots
        removeSlots.forEach(taskSlotsDAO::updateByIds);

        // Akka message for join.
        this.ClusterMessage.join(currentServer, removeSlots);
    }

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

    private void refreshNodes(Server server) {
        Node currentNode = new Node();
        currentNode.setServerId(server.getId());
        currentNode.setIp(server.getIp());
        currentNode.setAkkaAddress(server.getAkkaAddress());
        ClusterStatus.setCurrentNode(currentNode);

        List<Server> servers = serverDAO.listServers(ServerStatusConstant.OK.getStatus());
        Map<Long, Node> nodes = new HashMap<>();
        servers.forEach(s -> {
            Node node = new Node();
            node.setServerId(s.getId());
            node.setIp(s.getIp());
            node.setAkkaAddress(s.getAkkaAddress());
            nodes.put(s.getId(), node);
        });

        ClusterStatus.refreshNodes(nodes);
    }

    private Map<Long, List<Long>> computeSlots() {
        Map<Long, List<Long>> serverIdToSlots = Maps.newHashMap();
        taskSlotsDAO.listTaskSlots().forEach(taskSlots -> {
            if (taskSlots.getServerId() > 0) {
                List<Long> slots = serverIdToSlots.computeIfAbsent(taskSlots.getServerId(), m -> new ArrayList<>());
                slots.add(taskSlots.getId());
                serverIdToSlots.put(taskSlots.getServerId(), slots);
            }
        });

        // Remove server slots.
        int slotsServerCount = serverIdToSlots.size();
        Map<Long, List<Long>> serverRemoveSlots = Maps.newHashMap();
        serverIdToSlots.forEach((id, slots) -> {
            int remoteSize = (int) Math.floor(slots.size() * (1.0 / (slotsServerCount + 1)));
            List<Long> removeIds = slots.subList(0, remoteSize - 1);
            serverRemoveSlots.put(id, removeIds);
        });

        return serverRemoveSlots;
    }
}
