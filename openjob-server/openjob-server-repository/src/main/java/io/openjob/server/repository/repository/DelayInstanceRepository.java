package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.DelayInstance;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import scala.util.parsing.input.Page;

import java.awt.print.Pageable;
import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface DelayInstanceRepository extends JpaRepository<DelayInstance, Long> {

    /**
     * Batch update status.
     *
     * @param ids        ids
     * @param status     status
     * @param updateTime updateTime
     * @return Integer
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update DelayInstance as d set d.status=?2, d.updateTime=?3 where d.id in(?1)")
    Integer batchUpdateStatus(List<Long> ids, Integer status, Long updateTime);

    /**
     * Delete by task id.
     *
     * @param taskId taskId
     */
    void deleteByTaskId(String taskId);
}
