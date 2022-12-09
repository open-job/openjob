package io.openjob.server.repository.dao.impl;

import io.openjob.common.constant.CommonConstant;
import io.openjob.common.util.DateUtil;
import io.openjob.server.repository.constant.ServerStatusEnum;
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
        Long now = DateUtil.timestamp();
        server.setDeleted(CommonConstant.NO);
        server.setDeleteTime(0L);
        server.setCreateTime(now);
        server.setUpdateTime(now);
        server.setStatus(ServerStatusEnum.OK.getStatus());

        return serverRepository.saveAndFlush(server).getId();
    }

    @Override
    public Server getById(Long id) {
        return this.serverRepository.getById(id);
    }

    @Override
    public Optional<Server> getOne(String akkaAddress) {
        Server server = new Server();
        server.setAkkaAddress(akkaAddress);
        return serverRepository.findOne(Example.of(server));
    }

    @Override
    public Integer update(Long id, Integer status) {
        return serverRepository.update(id, status, DateUtil.timestamp());
    }

    @Override
    public Integer update(Long id, Integer status, Integer conditionStatus) {
        return this.serverRepository.updateByStatus(id, status, conditionStatus, DateUtil.timestamp());
    }

    @Override
    public List<Server> listServers(Integer status) {
        Server server = new Server();
        server.setStatus(status);
        return serverRepository.findAll(Example.of(server));
    }
}
