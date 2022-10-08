package io.openjob.worker.persistence;

import io.openjob.worker.entity.Delay;

import java.sql.SQLException;
import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface DelayPersistence {

    /**
     * Init table schema.
     */
    void initTable() throws Exception;

    /**
     * Batch save.
     *
     * @param delays delay task list.
     * @return Integer
     */
    Integer batchSave(List<Delay> delays) throws SQLException;

    /**
     * Update
     *
     * @param id id
     * @param size size
     * @param updateTime updateTime
     * @return Integer
     */
    Integer updatePullSizeById(Long id, Integer size, Integer updateTime) throws SQLException;

    /**
     * Find list.
     *
     * @return effect rows
     */
    List<Delay> findPullList() throws SQLException;

    /**
     * Delete by ids.
     *
     * @param ids ids
     * @return effect rows
     */
    Integer deleteByIds(List<Long> ids) throws SQLException;
}
