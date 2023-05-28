package io.openjob.server.repository.dao;

import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.dto.DelayInstancePageDTO;
import io.openjob.server.repository.dto.DelayInstanceTotalDTO;
import io.openjob.server.repository.dto.GroupCountDTO;
import io.openjob.server.repository.entity.DelayInstance;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public interface DelayInstanceDAO {

    /**
     * Batch save.
     *
     * @param delayInstanceList delay instance list.
     * @return save size.
     */
    Integer batchSave(List<DelayInstance> delayInstanceList);

    /**
     * Get by task id.
     *
     * @param taskId taskId
     * @return DelayInstance
     */
    DelayInstance getByTaskId(String taskId);

    /**
     * Get topic count
     *
     * @param topics   topics
     * @param statuses statuses
     * @return List
     */
    List<DelayInstanceTotalDTO> getTopicTotalCount(List<String> topics, List<Integer> statuses);

    /**
     * Update deleted
     *
     * @param taskid  taskid
     * @param deleted deleted
     */
    void updateDeleted(String taskid, Integer deleted);

    /**
     * Get page list
     *
     * @param instancePageDTO instancePageDTO
     * @return PageDTO
     */
    PageDTO<DelayInstance> pageList(DelayInstancePageDTO instancePageDTO);

    /**
     * Update status
     *
     * @param taskId taskId
     * @param status status
     * @return
     */
    Integer updateStatus(String taskId, Integer status);

    /**
     * Batch update status.
     *
     * @param updateList status
     * @return Integer
     */
    Integer batchUpdateStatus(List<DelayInstance> updateList);

    /**
     * Delete by task id.
     *
     * @param taskIds task id.
     * @return Integer
     */
    Integer deleteByTaskIds(List<String> taskIds);

    /**
     * Get first by delay id
     *
     * @param delayId delayId
     * @return DelayInstance
     */
    DelayInstance getFirstByDelayId(Long delayId);

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
     * @param status     status
     * @return Long
     */
    Long countTotalByNamespaceAndCreateTime(Long namespaceId, Long startTime, Long endTime, Integer status);

    /**
     * Delete by create time and status
     * @param lastTime lastTime
     * @param status status
     * @return Long
     */
    Long deleteByCreateTimeAndNotStatus(Long lastTime, Integer status);

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
}
