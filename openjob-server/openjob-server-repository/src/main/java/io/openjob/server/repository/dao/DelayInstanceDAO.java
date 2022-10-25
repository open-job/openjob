package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.DelayInstance;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface DelayInstanceDAO {

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
     * Save delay instance.
     *
     * @param delayInstance delayInstance
     * @return Long
     */
    Long save(DelayInstance delayInstance);

    /**
     * Batch update status.
     *
     * @param ids    ids
     * @param status status
     * @return Integer
     */
    Integer batchUpdateStatus(List<Long> ids, Integer status);
}
