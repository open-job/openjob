package io.openjob.server.repository.dao.impl;

import io.openjob.common.util.DateUtil;
import io.openjob.server.repository.constant.ServerStatusConstant;
import io.openjob.server.repository.dao.ServerDAO;
import io.openjob.server.repository.entity.Server;
import io.openjob.server.repository.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
        Integer now = DateUtil.now();
        server.setCreateTime(now);
        server.setUpdateTime(now);
        server.setStatus(ServerStatusConstant.OK.getStatus());

        return serverRepository.saveAndFlush(server).getId();
    }

    @Override
    public Optional<Server> getOne(String akkaAddress) {
        Server server = new Server();
        server.setAkkaAddress(akkaAddress);
        return serverRepository.findOne(Example.of(server));
    }

    @Override
    public Integer update(Long id, Integer status) {
        return serverRepository.update(id, status, DateUtil.now());
    }

    @Override
    public List<Server> listServers(Integer status) {
        Server server = new Server();
        server.setStatus(status);
        return serverRepository.findAll(Example.of(server));
    }
}
