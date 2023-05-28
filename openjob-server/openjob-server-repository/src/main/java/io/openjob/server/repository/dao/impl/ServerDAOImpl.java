package io.openjob.server.repository.dao.impl;

import io.openjob.common.constant.CommonConstant;
import io.openjob.common.util.DateUtil;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.constant.ServerStatusEnum;
import io.openjob.server.repository.dao.ServerDAO;
import io.openjob.server.repository.entity.Namespace;
import io.openjob.server.repository.entity.Server;
import io.openjob.server.repository.repository.ServerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author stelin swoft@qq.com
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

    @Override
    public List<Server> listServerByIds(List<Long> serverIds) {
        return this.serverRepository.findAllById(serverIds);
    }

    @Override
    public Long countByStatus(Integer status) {
        return this.serverRepository.countByStatusAndDeleted(status, CommonConstant.NO);
    }

    @Override
    public PageDTO<Server> pageList(String searchAddress, Integer page, Integer size) {
        // Matcher
        ExampleMatcher matching = ExampleMatcher.matching();
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
        Server server = new Server();
        server.setDeleted(CommonConstant.NO);

        // Search address.
        if (StringUtils.isNotEmpty(searchAddress)) {
            server.setAkkaAddress(searchAddress);
            matching = matching.withMatcher("akkaAddress", ExampleMatcher.GenericPropertyMatchers.contains());
        }

        // Condition
        Example<Server> example = Example.of(server, matching);
        Page<Server> pageResult = serverRepository.findAll(example, pageable);

        PageDTO<Server> paging = new PageDTO<>();
        if (!pageResult.isEmpty()) {
            paging.setPage(page);
            paging.setSize(size);
            paging.setList(pageResult.getContent());
            paging.setTotal(pageResult.getTotalElements());
        }
        return paging;
    }
}
