package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.ServerDAO;
import io.openjob.server.repository.entity.Server;
import io.openjob.server.repository.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class ServerDAOImpl implements ServerDAO {
    private final ServerRepository serverRepository;

    @Autowired
    public ServerDAOImpl(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public Long save(Server server) {
        return serverRepository.saveAndFlush(server).getId();
    }

    @Override
    public Integer update(Long id, Integer status) {
        return serverRepository.update(id, status, 1);
    }

    @Override
    public List<Server> listServers(Integer status) {
        return null;
    }
}
