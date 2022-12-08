package io.openjob.server.repository.dao.impl;

import io.openjob.common.util.DateUtil;
import io.openjob.server.repository.dao.ServerReportsDAO;
import io.openjob.server.repository.entity.ServerReports;
import io.openjob.server.repository.repository.ServerReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class ServerReportsImpl implements ServerReportsDAO {
    private final ServerReportsRepository serverReportsRepository;

    @Autowired
    public ServerReportsImpl(ServerReportsRepository serverReportsRepository) {
        this.serverReportsRepository = serverReportsRepository;
    }

    @Override
    public Long save(ServerReports serverFailReports) {
        serverFailReports.setCreateTime(DateUtil.now());
        return serverReportsRepository.saveAndFlush(serverFailReports).getId();
    }

    @Override
    public Long countServerReports(Integer startTime, Long serverId, Integer status) {
        return serverReportsRepository.countByCreateTimeGreaterThanEqualAndServerIdAndStatus(startTime, serverId, status);
    }
}
