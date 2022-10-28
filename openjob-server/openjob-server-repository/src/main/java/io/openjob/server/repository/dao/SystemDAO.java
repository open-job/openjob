package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.System;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface SystemDAO {

    /**
     * Get one.
     *
     * @return system
     */
    System getOne();

    /**
     * Update by id
     *
     * @param id             id
     * @param clusterVersion cluster version.
     * @return effect rows.
     */
    Integer updateClusterVersion(Integer id, Long clusterVersion);
}
