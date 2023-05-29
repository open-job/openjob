package io.openjob.server.log.dao;

import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.log.dto.ProcessorLogDTO;

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
     * @throws Exception Exception
     */
    void batchAdd(List<ProcessorLogDTO> jobInstanceTaskLogs) throws Exception;

    /**
     * Query by page.
     *
     * @param taskUniqueId taskUniqueId
     * @param time         time
     * @param size         size
     * @return List
     * @throws Exception Exception
     */
    List<ProcessorLogDTO> queryByScroll(String taskUniqueId, Long time, Integer size) throws Exception;

    /**
     * Query by page size
     *
     * @param taskUniqueId taskUniqueId
     * @param searchKey    searchKey
     * @param page         page
     * @param size         size
     * @return PageDTO
     * @throws Exception Exception
     */
    PageDTO<ProcessorLogDTO> queryByPageSize(String taskUniqueId, String searchKey, Integer page, Integer size) throws Exception;

    /**
     * Delete by last time
     *
     * @param lastTime lastTime
     * @throws Exception Exception
     */
    void deleteByLastTime(Long lastTime) throws Exception;
}
