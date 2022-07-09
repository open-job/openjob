package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.JobInstance;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface JobInstanceDAO {
    Long save(JobInstance jobInstance);

    Long update(JobInstance jobInstance);
}
