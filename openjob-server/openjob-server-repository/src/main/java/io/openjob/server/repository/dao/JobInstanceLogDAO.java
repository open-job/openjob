package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.JobInstanceLog;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public interface JobInstanceLogDAO {

    /**
     * Save
     *
     * @param jobInstanceLog jobInstanceLog
     * @return Long
     */
    Long save(JobInstanceLog jobInstanceLog);

    /**
     * Get by job instance id.
     *
     * @param jobInstanceId jobInstanceId
     * @return JobInstanceLog
     */
    List<JobInstanceLog> getByJobInstanceId(Long jobInstanceId);
}
