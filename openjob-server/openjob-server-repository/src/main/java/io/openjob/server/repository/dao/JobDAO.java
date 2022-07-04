package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.Job;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface JobDAO {
    /**
     * Save job.
     *
     * @param job job
     * @return Long
     */
    Long save(Job job);
}
