package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.ServerFailReports;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface ServerFailReportsDAO {

    /**
     * Save server fail reports.
     *
     * @param serverFailReports serverFailReports
     * @return Save id.
     */
    Long save(ServerFailReports serverFailReports);

    /**
     * Fail reported count.
     *
     * @param startTime startTime
     * @return Long
     */
    Long countServerFailReports(Integer startTime);
}
