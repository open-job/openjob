package io.openjob.server.cluster.service;

import io.openjob.server.cluster.MessageDispatcher;
import io.openjob.server.cluster.cluster.Cluster;
import io.openjob.server.cluster.cluster.Node;
import io.openjob.server.repository.constant.ServerStatusConstant;
import io.openjob.server.repository.dao.ServerDAO;
import io.openjob.server.repository.entity.Server;
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
    private final MessageDispatcher messageManager;

    @Autowired
    public ServerService(ServerDAO serverDAO, MessageDispatcher messageManager) {
        this.serverDAO = serverDAO;
        this.messageManager = messageManager;
    }


    public void join(String hostname, Integer port) {
        Server server = this.registerServer(hostname, port);

        this.refreshNodes();

        this.messageManager.join(server);
    }

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
        Cluster.refreshNodes(nodes);
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

}
