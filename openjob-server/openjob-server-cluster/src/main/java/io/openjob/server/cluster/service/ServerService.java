package io.openjob.server.cluster.service;

import io.openjob.server.repository.constant.ServerStatusConstant;
import io.openjob.server.repository.dao.ServerDAO;
import io.openjob.server.repository.entity.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Service
public class ServerService {
    private final ServerDAO serverDAO;

    @Autowired
    public ServerService(ServerDAO serverDAO) {
        this.serverDAO = serverDAO;
    }

    public Long registerServer(String hostname, Integer port) {
        // Has been registered
        String akkaAddress = String.format("%s:%d", hostname, port);
        Optional<Server> optionalServer = serverDAO.getOne(akkaAddress);
        if (optionalServer.isPresent()) {
            // Update status
            Long id = optionalServer.get().getId();
            serverDAO.update(id, ServerStatusConstant.OK.getStatus());
            return id;
        }

        // First register server
        Server server = new Server();
        server.setIp(hostname);
        server.setAkkaAddress(akkaAddress);
        return serverDAO.save(server);
    }
}
