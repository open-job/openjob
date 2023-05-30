package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public interface ServerRepository extends JpaRepository<Server, Long> {
    /**
     * Update server by id.
     *
     * @param id              id
     * @param status          status
     * @param conditionStatus condition status.
     * @param updateTime      updateTime
     * @return Effected rows.
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update Server as s set s.status=?2,s.updateTime=?4 where s.id=?1 and s.status=?3")
    Integer updateByStatus(Long id, Integer status, Integer conditionStatus, Long updateTime);

    /**
     * Update server by id.
     *
     * @param id         id
     * @param status     status
     * @param updateTime updateTime
     * @return Effected rows.
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update Server as s set s.status=?2,s.updateTime=?3 where s.id=?1")
    Integer update(Long id, Integer status, Long updateTime);

    /**
     * Count by status
     *
     * @param status  status
     * @param deleted deleted
     * @return Long
     */
    Long countByStatusAndDeleted(Integer status, Integer deleted);

    /**
     * Delete sever by create time and status
     *
     * @param lastTime lastTime
     * @param status   status
     * @return Long
     */
    @Modifying
    @Transactional
    Long deleteByCreateTimeLessThanEqualAndStatus(Long lastTime, Integer status);
}
