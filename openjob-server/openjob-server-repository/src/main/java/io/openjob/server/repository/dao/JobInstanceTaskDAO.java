package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.JobInstanceTask;
import scala.Int;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface JobInstanceTaskDAO {

    /**
     * Save
     *
     * @param jobInstanceTask jobInstanceTask
     * @return Long
     */
    Long save(JobInstanceTask jobInstanceTask);

    /**
     * Batch save.
     *
     * @param taskList taskList
     * @return Integer
     */
    Integer batchSave(List<JobInstanceTask> taskList);
}
