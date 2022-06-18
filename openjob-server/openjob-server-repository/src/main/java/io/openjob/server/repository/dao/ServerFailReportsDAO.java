package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.Server;
import io.openjob.server.repository.entity.ServerFailReports;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface ServerFailReportsDAO {

    Long save(ServerFailReports serverFailReports);

    Long countServerFailReports(Integer startTime);
}
