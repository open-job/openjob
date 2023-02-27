package io.openjob.server.repository.dao;

import io.openjob.common.constant.InstanceStatusEnum;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.dto.JobInstancePageDTO;
import io.openjob.server.repository.entity.JobInstance;

import java.util.List;
import java.util.Set;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface JobInstanceDAO {
    /**
     * Save
     *
     * @param jobInstance jobInstance
     * @return Insert id.
     */
    Long save(JobInstance jobInstance);

    /**
     * Update
     *
     * @param id     id
     * @param status status
     * @return Integer
     */
    Integer updateStatusById(Long id, Integer status);

    /**
     * Update
     *
     * @param ids            ids
     * @param lastReportTime lastReportTime
     * @return Integer
     */
    Integer updateLastReportTimeByIds(List<Long> ids, Long lastReportTime);

    /**
     * Get failover list.
     *
     * @param slotsIds    slots id.
     * @param executeTime execute time.
     * @param status      status list.
     * @return List
     */
    List<JobInstance> getUnDispatchedList(Set<Long> slotsIds, Long executeTime, InstanceStatusEnum status);

    /**
     * Get failover list.
     *
     * @param slotsIds       slots id.
     * @param lastReportTime last report time.
     * @param status         status list.
     * @return List
     */
    List<JobInstance> getFailoverList(Set<Long> slotsIds, Long lastReportTime, InstanceStatusEnum status);

    /**
     * Update to running.
     *
     * @param id                 id
     * @param workerAddress      worker address
     * @param instanceStatusEnum instance status
     * @param lastReportTime     last report time.
     * @return Integer
     */
    Integer updateByRunning(Long id, String workerAddress, InstanceStatusEnum instanceStatusEnum, Long lastReportTime);

    /**
     * Get one by id and status.
     *
     * @param jobId  jobId
     * @param id     id
     * @param status status
     * @return JobInstance
     */
    JobInstance getOneByJobIdAndStatus(Long jobId, Long id, Integer status);

    /**
     * Get page list.
     *
     * @param jobInstancePageDTO jobInstancePageDTO
     * @return PageDTO
     */
    PageDTO<JobInstance> pageList(JobInstancePageDTO jobInstancePageDTO);
}
