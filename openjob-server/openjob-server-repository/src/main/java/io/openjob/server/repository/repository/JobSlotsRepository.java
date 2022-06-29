package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.JobSlots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface JobSlotsRepository extends JpaRepository<JobSlots, Long> {
    @Modifying
    @Transactional
    @Query("update JobSlots set serverId=?2,createTime=?3 where id in (?1)")
    Long updateByIds(List<Long> ids, Long serverId, Integer updateTime);

    List<JobSlots> findAllForUpdate();
}