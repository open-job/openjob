package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.ServerReports;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface ServerReportsRepository extends JpaRepository<ServerReports, Long> {

    /**
     * Count by condition.
     *
     * @param createTime create time.
     * @param serverId   server id.
     * @param status     status
     * @return Long
     */
    Long countByCreateTimeGreaterThanEqualAndServerIdAndStatus(Long createTime, Long serverId, Integer status);
}
