package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.ServerReports;

/**
 * @author stelin swoft@qq.com
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
     * Count reports.
     *
     * @param startTime start time.
     * @param serverId  server id.
     * @param status    status.
     * @return Long
     */
    Long countServerReports(Long startTime, Long serverId, Integer status);
}
