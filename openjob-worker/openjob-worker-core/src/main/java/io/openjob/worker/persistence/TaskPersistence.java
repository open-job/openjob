package io.openjob.worker.persistence;

import io.openjob.worker.entity.Task;

import java.sql.SQLException;
import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public interface TaskPersistence {

    /**
     * Init table schema.
     *
     * @throws Exception Exception
     */
    void initTable() throws Exception;

    /**
     * Batch save.
     *
     * @param tasks task list.
     * @return Integer
     * @throws SQLException SQLException
     */
    Integer batchSave(List<Task> tasks) throws SQLException;

    /**
     * Find by task id.
     *
     * @param taskId task id.
     * @return Task
     * @throws SQLException SQLException
     */
    Task findByTaskId(String taskId) throws SQLException;

    /**
     * Batch delete by task ids.
     *
     * @param taskIds taskIds
     * @return Integer
     * @throws SQLException SQLException
     */
    Integer batchDeleteByTaskIds(List<String> taskIds) throws SQLException;

    /**
     * Count task by status list.
     *
     * @param instanceId instanceId
     * @param circleId   circleId
     * @param statusList statusList
     * @return count
     * @throws SQLException SQLException
     */
    Integer countTask(Long instanceId, Long circleId, List<Integer> statusList) throws SQLException;


    /**
     * Count task by status list and exclude id.
     *
     * @param instanceId    instanceId
     * @param circleId      circleId
     * @param statusList    statusList
     * @param excludeTaskId excludeTaskId
     * @return count
     * @throws SQLException SQLException
     */
    Integer countTaskAndExcludeId(Long instanceId, Long circleId, List<Integer> statusList, String excludeTaskId) throws SQLException;

    /**
     * Update status by id
     *
     * @param taskId taskId
     * @param status status
     * @return Integer
     * @throws SQLException SQLException
     */
    Integer updateStatusByTaskId(String taskId, Integer status) throws SQLException;

    /**
     * Batch update by task id.
     *
     * @param tasks         tasks
     * @param currentStatus currentStatus
     * @return Integer
     * @throws SQLException SQLException
     */
    Integer batchUpdateStatusByTaskId(List<Task> tasks, Integer currentStatus) throws SQLException;

    /**
     * Batch update status and worker address.
     *
     * @param taskIds       task ids
     * @param status        status
     * @param workerAddress worker address
     * @return Integer
     * @throws SQLException SQLException
     */
    Integer batchUpdateStatusAndWorkerAddressByTaskId(List<String> taskIds, Integer status, String workerAddress) throws SQLException;

    /**
     * Get redundant map task
     *
     * @param parentTaskId  parentTaskId
     * @param lastMapTaskId lastMapTaskId
     * @return Integer
     */
    Integer deleteRedundantMapTask(String parentTaskId, Long lastMapTaskId) throws SQLException;

    /**
     * Get redundant map task
     *
     * @param parentTaskId  parentTaskId
     * @param mapTaskIdList mapTaskIdList
     * @return List
     */
    List<Long> getMapTaskList(String parentTaskId, List<Long> mapTaskIdList) throws SQLException;

    /**
     * Find list by page size
     *
     * @param instanceId instance id
     * @param circleId   circle id
     * @param size       size
     * @return List
     * @throws SQLException SQLException
     */
    List<Task> findListBySize(Long instanceId, Long circleId, Long size) throws SQLException;

    /**
     * Find circle parent task list
     *
     * @param instanceId   instanceId
     * @param circleId     circleId
     * @param parentTaskId parentTaskId
     * @return List
     * @throws SQLException SQLException
     */
    List<Task> findCircleParentTaskList(Long instanceId, Long circleId, String parentTaskId) throws SQLException;

    /**
     * Find child task list
     *
     * @param taskId taskId
     * @return List
     * @throws SQLException SQLException
     */
    List<Task> findChildTaskList(String taskId) throws SQLException;

    /**
     * Batch update exception by worker address list.
     *
     * @param workerAddressList worker address list
     * @return Integer
     * @throws SQLException SQLException
     */
    Integer batchUpdateFailoverByWorkerAddress(List<String> workerAddressList) throws SQLException;

    /**
     * Pull exception list.
     *
     * @param instanceId instance id
     * @param size       size
     * @return List
     * @throws SQLException SQLException
     */
    List<Task> pullFailoverListBySize(Long instanceId, Long size) throws SQLException;
}
