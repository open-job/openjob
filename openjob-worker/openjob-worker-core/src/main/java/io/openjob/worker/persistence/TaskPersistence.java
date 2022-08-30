package io.openjob.worker.persistence;

import io.openjob.worker.entity.Task;

import java.sql.SQLException;
import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface TaskPersistence {

    /**
     * Init table schema.
     */
    void initTable() throws Exception;

    /**
     * Batch save.
     *
     * @param tasks task list.
     * @return Integer
     */
    Integer batchSave(List<Task> tasks) throws SQLException;

    /**
     * Find by task id.
     *
     * @param taskId task id.
     * @return Task
     */
    Task findByTaskId(String taskId) throws SQLException;

    Integer batchDeleteByTaskIds(List<String> taskIds) throws SQLException;

    /**
     * Count task by status list.
     *
     * @param instanceId instanceId
     * @param statusList statusList
     * @return count
     */
    Integer countTask(Long instanceId, Long circleId, List<Integer> statusList) throws SQLException;

    /**
     * Batch update by task id.
     *
     * @param tasks         tasks
     * @param currentStatus currentStatus
     * @return Integer
     */
    Integer batchUpdateStatusByTaskId(List<Task> tasks, Integer currentStatus) throws SQLException;
}
