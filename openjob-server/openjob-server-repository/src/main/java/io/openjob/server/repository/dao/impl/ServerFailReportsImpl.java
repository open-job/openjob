package io.openjob.server.repository.dao.impl;

import io.openjob.common.util.DateUtil;
import io.openjob.server.repository.dao.ServerFailReportsDAO;
import io.openjob.server.repository.entity.ServerFailReports;
import io.openjob.server.repository.repository.ServerFailReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class ServerFailReportsImpl implements ServerFailReportsDAO {
    private final ServerFailReportsRepository serverFailReportsRepository;

    @Autowired
    public ServerFailReportsImpl(ServerFailReportsRepository serverFailReportsRepository) {
        this.serverFailReportsRepository = serverFailReportsRepository;
    }

    @Override
    public Long save(ServerFailReports serverFailReports) {
        serverFailReports.setCreateTime(DateUtil.now());
        return serverFailReportsRepository.saveAndFlush(serverFailReports).getId();
    }

    @Override
    public Long countServerFailReports(Integer startTime) {
        return serverFailReportsRepository.countByCreateTimeGreaterThanEqual(startTime);
    }
}
