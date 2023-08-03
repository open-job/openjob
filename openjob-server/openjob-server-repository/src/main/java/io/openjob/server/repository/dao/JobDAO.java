package io.openjob.server.repository.dao;

import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.dto.JobPageDTO;
import io.openjob.server.repository.entity.Job;

import java.util.List;

/**
 * @author stelin swoft@qq.com
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
     * Update job
     *
     * @param job job
     * @return Long
     */
    Long update(Job job);

    /**
     * Update
     *
     * @param id              id
     * @param nextExecuteTime nextExecuteTime
     * @param updateTime      updateTime
     * @return Long
     */
    Long updateNextExecuteTime(Long id, Long nextExecuteTime, Long updateTime);

    /**
     * Update by status or deleted.
     *
     * @param id              id
     * @param status          status
     * @param deleted         deleted
     * @param nextExecuteTime nextExecuteTime
     * @return Long
     */
    Long updateByStatusOrDeleted(Long id, Integer status, Integer deleted, Long nextExecuteTime);

    /**
     * Get by id
     *
     * @param id id
     * @return Job
     */
    Job getById(Long id);

    /**
     * Get by ids
     *
     * @param ids ids
     * @return List
     */
    List<Job> getByIds(List<Long> ids);

    /**
     * Get first by namespace id and appid
     *
     * @param namespaceId namespaceId
     * @param appId       appId
     * @return Job
     */
    Job getFirstByNamespaceAndAppid(Long namespaceId, Long appId);

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
     * Count by namespace
     *
     * @param namespaceId namespaceId
     * @return Long
     */
    Long countByNamespace(Long namespaceId);

    /**
     * Count by namespace and create time
     *
     * @param namespaceId namespaceId
     * @param startTime   startTime
     * @param endTime     endTime
     * @return Long
     */
    Long countByNamespaceAndCreateTime(Long namespaceId, Long startTime, Long endTime);


    /**
     * Get page list.
     *
     * @param jobPageDTO jobPageDTO
     * @return PageDTO
     */
    PageDTO<Job> pageList(JobPageDTO jobPageDTO);
}
