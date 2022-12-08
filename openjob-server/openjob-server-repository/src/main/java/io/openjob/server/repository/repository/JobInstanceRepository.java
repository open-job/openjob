package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.JobInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface JobInstanceRepository extends JpaRepository<JobInstance, Long> {

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
     * @param id             id
     * @param lastReportTime lastReportTime
     * @param updateTime     updateTime
     * @return Integer
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update JobInstance as j set j.lastReportTime=?2,j.updateTime=?3 where j.id=?1")
    Integer update(Long id, Long lastReportTime, Long updateTime);
}
