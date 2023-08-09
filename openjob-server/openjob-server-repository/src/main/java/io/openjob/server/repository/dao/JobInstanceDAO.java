package io.openjob.server.repository.dao;

import io.openjob.common.constant.InstanceStatusEnum;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.dto.GroupCountDTO;
import io.openjob.server.repository.dto.JobInstancePageDTO;
import io.openjob.server.repository.entity.JobInstance;

import java.util.List;
import java.util.Set;

/**
 * @author stelin swoft@qq.com
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
     * Delete by id
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * Update
     *
     * @param id         id
     * @param status     status
     * @param failStatus failStatus
     * @return Integer
     */
    Integer updateStatusById(Long id, Integer status, Integer failStatus);

    /**
     * Update
     *
     * @param ids            ids
     * @param lastReportTime lastReportTime
     * @return Integer
     */
    Integer updateLastReportTimeByIds(List<Long> ids, Long lastReportTime);

    /**
     * Get by id.
     *
     * @param id id
     * @return JobInstance
     */
    JobInstance getById(Long id);

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
     * Update dispatch version
     *
     * @param id id
     * @return Integer
     */
    Integer updateDispatchVersion(Long id);

    /**
     * Get one by id and status.
     *
     * @param jobId      jobId
     * @param id         id
     * @param statusList statusList
     * @return JobInstance
     */
    JobInstance getOneByJobIdAndStatus(Long jobId, Long id, List<Integer> statusList);

    /**
     * Get first by job id
     *
     * @param jobId jobId
     * @return JobInstance
     */
    JobInstance getFirstByJobId(Long jobId);

    /**
     * Get page list.
     *
     * @param jobInstancePageDTO jobInstancePageDTO
     * @return PageDTO
     */
    PageDTO<JobInstance> pageList(JobInstancePageDTO jobInstancePageDTO);

    /**
     * Count total
     *
     * @param namespaceId namespaceId
     * @return Long
     */
    Long countTotalByNamespace(Long namespaceId);

    /**
     * Count by create time
     *
     * @param namespaceId namespaceId
     * @param startTime   startTime
     * @param endTime     endTime
     * @param status      status
     * @return Long
     */
    Long countTotalByNamespaceAndCreateTime(Long namespaceId, Long startTime, Long endTime, Integer status);

    /**
     * Delete by create time and status
     *
     * @param lastTime lastTime
     * @return Long
     */
    Long deleteByCreateTim(Long lastTime);

    /**
     * Group by hour time
     *
     * @param namespaceId namespaceId
     * @param startTime   startTime
     * @param endTime     endTime
     * @param status      status
     * @return List
     */
    List<GroupCountDTO> countByNamespaceGroupByHourTime(Long namespaceId, Long startTime, Long endTime, Integer status);

    /**
     * Group by date time
     *
     * @param namespaceId namespaceId
     * @param startTime   startTime
     * @param endTime     endTime
     * @param status      status
     * @return List
     */
    List<GroupCountDTO> countByNamespaceGroupByDateTime(Long namespaceId, Long startTime, Long endTime, Integer status);


    /**
     * Group by status
     *
     * @param namespaceId namespaceId
     * @param startTime   startTime
     * @param endTime     endTime
     * @return List
     */
    List<GroupCountDTO> countByNamespaceGroupByStatus(Long namespaceId, Long startTime, Long endTime);
}
