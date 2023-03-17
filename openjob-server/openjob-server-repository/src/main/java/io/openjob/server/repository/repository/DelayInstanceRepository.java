package io.openjob.server.repository.repository;

import io.openjob.server.repository.dto.DelayInstanceTotalDTO;
import io.openjob.server.repository.entity.DelayInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface DelayInstanceRepository extends JpaRepository<DelayInstance, Long>, JpaSpecificationExecutor<DelayInstance> {

    /**
     * Batch delete by task ids.
     *
     * @param taskIds    taskIds
     * @param deleted    deleted
     * @param deleteTime deleteTime
     * @return deleteTime
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update DelayInstance as d set d.deleted=?2, d.deleteTime=?3 where d.taskId in(?1)")
    Integer batchDeleteByTaskIds(List<String> taskIds, Integer deleted, Long deleteTime);

    /**
     * Get topic total count
     *
     * @param topics   topics
     * @param statuses statuses
     * @return List
     */
    @Query(value = "SELECT new io.openjob.server.repository.dto.DelayInstanceTotalDTO(d.topic, count(d.id)) from DelayInstance as d where d.topic in (?1) and d.status in (?2)")
    List<DelayInstanceTotalDTO> getDelayTotalCount(List<String> topics, List<Integer> statuses);

    /**
     * Find by task id.
     *
     * @param taskId task id.
     */
    DelayInstance findByTaskId(String taskId);
}
