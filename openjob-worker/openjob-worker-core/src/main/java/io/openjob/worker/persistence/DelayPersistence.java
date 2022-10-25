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
     * @throws Exception Exception
     */
    void initTable() throws Exception;

    /**
     * Batch save.
     *
     * @param delays delay task list.
     * @return Effect rows
     * @throws SQLException SQLException
     */
    Integer batchSave(List<Delay> delays) throws SQLException;

    /**
     * Update
     *
     * @param id         id
     * @param size       size
     * @param updateTime updateTime
     * @return Effect rows
     * @throws SQLException SQLException
     */
    Integer updatePullSizeById(Long id, Integer size, Integer updateTime) throws SQLException;

    /**
     * Batch upadte.
     *
     * @param delays delays
     * @return Effect rows
     * @throws SQLException SQLException
     */
    Integer batchUpdatePullTime(List<Delay> delays) throws SQLException;

    /**
     * Find list.
     *
     * @return Effect rows
     * @throws SQLException SQLException
     */
    List<Delay> findPullList() throws SQLException;

    /**
     * Delete by ids.
     *
     * @param ids ids
     * @return Effect rows
     * @throws SQLException SQLException
     */
    Integer deleteByIds(List<Long> ids) throws SQLException;

}
