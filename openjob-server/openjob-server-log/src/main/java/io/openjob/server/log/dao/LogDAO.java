package io.openjob.server.log.dao;

import io.openjob.server.log.dto.ProcessorLog;

import java.sql.SQLException;
import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public interface LogDAO {

    /**
     * Batch add.
     *
     * @param jobInstanceTaskLogs jobInstanceTaskLogs
     * @throws SQLException SQLException
     */
    void batchAdd(List<ProcessorLog> jobInstanceTaskLogs) throws SQLException;

    /**
     * Query by page.
     *
     * @param taskUniqueId taskUniqueId
     * @param time         time
     * @param size         size
     * @return List
     * @throws SQLException SQLException
     */
    List<ProcessorLog> queryByPage(String taskUniqueId, Long time, Long size) throws SQLException;
}
