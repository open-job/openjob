package io.openjob.server.repository.dao;

import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.dto.DelayInstancePageDTO;
import io.openjob.server.repository.dto.DelayInstanceTotalDTO;
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
     * List delay instance.
     *
     * @param slotIds slotIds
     * @param time    time
     * @param size    size
     * @return List
     */
    List<DelayInstance> listDelayInstance(List<Long> slotIds, Integer time, Integer size);

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
}
