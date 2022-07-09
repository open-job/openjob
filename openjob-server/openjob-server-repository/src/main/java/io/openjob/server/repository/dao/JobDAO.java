package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.Job;

import java.util.List;

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

    List<Job> listScheduledJobs(List<Long> slotIds, Integer time);

    List<Job> listScheduledSecondJobs(List<Long> slotIds);
}
