package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.Job;
import org.springframework.data.domain.Page;

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

    /**
     * List scheduled jobs.
     *
     * @param slotIds slotIds
     * @param time    time
     * @return List
     */
    List<Job> listScheduledJobs(List<Long> slotIds, Long time);

    /**
     * List seconds scheduled jobs.
     *
     * @param slotIds slotIds
     * @return List
     */
    List<Job> listScheduledSecondJobs(List<Long> slotIds);

    /**
     * job list
     *
     * @param page page
     * @param size size
     * @return Page<job>
     */
    Page<Job> list(Integer page, Integer size);
}
