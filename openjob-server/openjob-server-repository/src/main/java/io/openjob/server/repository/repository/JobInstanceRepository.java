package io.openjob.server.repository.repository;

import io.openjob.server.repository.dto.GroupCountDTO;
import io.openjob.server.repository.entity.JobInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public interface JobInstanceRepository extends JpaRepository<JobInstance, Long>, JpaSpecificationExecutor<JobInstance> {

    /**
     * Update for status.
     *
     * @param id           id
     * @param status       status
     * @param completeTime completeTime
     * @param updateTime   updateTime
     * @return Integer
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update JobInstance as j set j.status=?2,j.completeTime=?3,j.updateTime=?4 where j.id=?1")
    Integer update(Long id, Integer status, Long completeTime, Long updateTime);

    /**
     * Update for last report time.
     *
     * @param ids            ids
     * @param lastReportTime lastReportTime
     * @return Integer
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update JobInstance as j set j.lastReportTime=?2,j.updateTime=?2 where j.id in (?1)")
    Integer updateLastReportTimeByIds(List<Long> ids, Long lastReportTime);

    /**
     * Update by running
     *
     * @param id             id
     * @param workerAddress  worker address.
     * @param status         status
     * @param lastReportTime last report time.
     * @return Integer
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update JobInstance as j set j.workerAddress=?2,j.status=?3,j.updateTime=?4,j.lastReportTime=?4 where j.id=?1")
    Integer updateByRunning(Long id, String workerAddress, Integer status, Long lastReportTime);

    /**
     * Find failover list.
     *
     * @param lastReportTime last report time
     * @param slotsIds       slots ids
     * @param status         status
     * @param type           type
     * @param deleted        deleted
     * @return List
     */
    List<JobInstance> findByLastReportTimeLessThanAndSlotsIdInAndStatusAndTimeExpressionTypeNotAndDeleted(
            Long lastReportTime, Set<Long> slotsIds, Integer status, String type, Integer deleted);

    /**
     * Find not dispatch list.
     *
     * @param executeTime execute time
     * @param slotsIds    slots ids.
     * @param status      status.
     * @param deleted     deleted
     * @return list
     */
    List<JobInstance> findByExecuteTimeLessThanAndSlotsIdInAndStatusAndDeleted(Long executeTime, Set<Long> slotsIds, Integer status, Integer deleted);

    /**
     * Find first by id and status.
     *
     * @param jobId      jobId
     * @param id         id
     * @param statusList statusList
     * @param deleted    deleted
     * @return JobInstance
     */
    JobInstance findFirstByJobIdAndIdNotAndStatusInAndDeleted(Long jobId, Long id, List<Integer> statusList, Integer deleted);

    /**
     * Find first by job id and deleted
     *
     * @param jobId   jobId
     * @param deleted deleted
     * @return JobInstance
     */
    JobInstance findFirstByJobIdAndDeleted(Long jobId, Integer deleted);

    /**
     * Count total
     *
     * @param namespaceId namespaceId
     * @param deleted     deleted
     * @return Long
     */
    Long countByNamespaceIdAndDeleted(Long namespaceId, Integer deleted);

    /**
     * Group by hour time
     *
     * @param namespaceId namespaceId
     * @param startTime   startTime
     * @param endTime     endTime
     * @param status      status
     * @param deleted     deleted
     * @return List
     */
    @Query(value = "SELECT new io.openjob.server.repository.dto.GroupCountDTO(j.createTimeHour, count(j.id)) from JobInstance as j "
            + "where j.namespaceId=?1 and j.createTime >= ?2 and j.createTime<=?3 and j.status=?4 and j.deleted=?5")
    List<GroupCountDTO> getJobInstanceGroupByHour(Long namespaceId, Long startTime, Long endTime, Integer status, Integer deleted);

}
