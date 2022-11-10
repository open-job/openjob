package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.System;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface SystemRepository extends JpaRepository<System, Integer> {
    /**
     * Update cluster version
     *
     * @param id id
     * @return effect rows.
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query("update System set clusterVersion=clusterVersion+1 where id=?1")
    Integer updateClusterVersion(Integer id);
}
