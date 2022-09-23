package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.JobInstanceLog;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface JobInstanceLogDAO {
    Long save(JobInstanceLog jobInstanceLog);
}
