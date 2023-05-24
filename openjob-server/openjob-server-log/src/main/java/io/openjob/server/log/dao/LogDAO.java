package io.openjob.server.log.dao;

import io.openjob.server.log.dto.ProcessorLogDTO;

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
    void batchAdd(List<ProcessorLogDTO> jobInstanceTaskLogs) throws Exception;

    /**
     * Query by page.
     *
     * @param taskUniqueId taskUniqueId
     * @param time         time
     * @param size         size
     * @return List
     * @throws SQLException SQLException
     */
    List<ProcessorLogDTO> queryByPage(String taskUniqueId, Long time, Long size) throws Exception;

    /**
     * Query by page size
     *
     * @param taskUniqueId taskUniqueId
     * @param searchKey    searchKey
     * @param page         page
     * @param size         size
     * @return List
     * @throws Exception Exception
     */
    List<ProcessorLogDTO> queryByPageSize(String taskUniqueId, String searchKey, Long page, Long size) throws Exception;

    /**
     * Delete log before days
     *
     * @param beforeDays beforeDays
     */
    void deleteByDays(Integer beforeDays);
}
