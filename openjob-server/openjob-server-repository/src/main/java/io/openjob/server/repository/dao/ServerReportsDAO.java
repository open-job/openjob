package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.ServerReports;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface ServerReportsDAO {

    /**
     * Save server fail reports.
     *
     * @param serverReports serverReports
     * @return Save id.
     */
    Long save(ServerReports serverReports);

    /**
     * Fail reported count.
     *
     * @param startTime startTime
     * @return Long
     */
    Long countServerReports(Integer startTime, Long serverId, Integer status);
}
