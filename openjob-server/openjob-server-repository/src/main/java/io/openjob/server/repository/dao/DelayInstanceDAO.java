package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.DelayInstance;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
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
