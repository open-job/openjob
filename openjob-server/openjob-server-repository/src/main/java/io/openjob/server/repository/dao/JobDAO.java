package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.Job;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface JobDAO {
    Long save(Job job);
}
