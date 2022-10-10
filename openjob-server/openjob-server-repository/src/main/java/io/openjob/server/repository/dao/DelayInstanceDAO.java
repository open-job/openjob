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
     * @return List
     */
    List<DelayInstance> listDelayInstance(List<Long> slotIds, Integer time);

    /**
     * Save delay instance.
     *
     * @param delayInstance delayInstance
     * @return Long
     */
    Long save(DelayInstance delayInstance);
}
