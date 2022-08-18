package io.openjob.worker.persistence;

import io.openjob.worker.entity.Task;

import java.sql.SQLException;
import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface TaskPersistence {
    void initTable() throws Exception;

    Integer batchSave(List<Task> tasks) throws SQLException;

    Task findByTaskId(String taskId) throws SQLException;
}
