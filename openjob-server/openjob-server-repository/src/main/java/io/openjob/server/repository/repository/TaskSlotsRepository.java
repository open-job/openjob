package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.TaskSlots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface TaskSlotsRepository extends JpaRepository<TaskSlots, Long> {
    @Modifying
    @Transactional
    @Query("update TaskSlots set serverId=?2,createTime=?3 where id in (?1)")
    Long updateByIds(List<Long> ids, Long serverId, Integer updateTime);
}