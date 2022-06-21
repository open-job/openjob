package io.openjob.server.cluster.service;

import io.openjob.server.cluster.message.ClusterMessage;
import io.openjob.server.cluster.ClusterStatus;
import io.openjob.server.cluster.context.Node;
import io.openjob.server.repository.constant.ServerStatusConstant;
import io.openjob.server.repository.dao.ServerDAO;
import io.openjob.server.repository.dao.TaskSlotsDAO;
import io.openjob.server.repository.entity.Server;
import io.openjob.server.repository.entity.TaskSlots;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        // Register server.
        Server server = this.registerServer(hostname, port);

        // Refresh nodes.
        List<Server> servers = this.refreshNodes(server);

        // Refresh slots.
        this.refreshSlots(servers);

        // Akka message for join.
        this.ClusterMessage.join(server);
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

    private List<Server> refreshNodes(Server server) {
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

        return servers;
    }

    public void refreshSlots(List<Server> servers) {
        List<TaskSlots> taskSlots = taskSlotsDAO.listTaskSlots();
    }
}
