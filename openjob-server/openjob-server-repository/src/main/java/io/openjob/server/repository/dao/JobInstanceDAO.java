package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.JobInstance;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface JobInstanceDAO {
    /**
     * Save
     *
     * @param jobInstance jobInstance
     * @return Insert id.
     */
    Long save(JobInstance jobInstance);

    /**
     * Update
     *
     * @param id           id
     * @param status       status
     * @return Integer
     */
    Integer updateStatusAndCompleteTimeById(Long id, Integer status);

    /**
     * Update
     *
     * @param id             id
     * @param lastReportTime lastReportTime
     * @return Integer
     */
    Integer updateLastReportTimeById(Long id, Integer lastReportTime);
}
