package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.JobSlots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface JobSlotsRepository extends JpaRepository<JobSlots, Long> {
    @Modifying
    @Transactional
    @Query("update JobSlots set serverId=:serverId,updateTime=:updateTime where id in (:ids)")
    Integer updateByServerId(@Param("ids") List<Long> ids, @Param("serverId") Long serverId, @Param("updateTime") Integer updateTime);

    @Modifying
    @Transactional
    @Query("update JobSlots set serverId=?1,updateTime=?2")
    Integer updateByServerId(Long serverId, Integer updateTime);
}