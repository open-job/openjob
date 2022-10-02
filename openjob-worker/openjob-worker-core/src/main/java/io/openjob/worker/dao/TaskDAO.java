package io.openjob.worker.dao;

import io.openjob.common.util.DateUtil;
import io.openjob.worker.entity.Task;
import io.openjob.worker.persistence.H2TaskMemoryPersistence;
import io.openjob.worker.persistence.TaskPersistence;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
public class TaskDAO {
    public final static TaskDAO INSTANCE = new TaskDAO();
    private final TaskPersistence taskPersistence;

    private TaskDAO() {
        this.taskPersistence = new H2TaskMemoryPersistence();
    }

    public Boolean add(Task task) {
        try {
            Integer now = DateUtil.now();
            task.setUpdateTime(now);
            task.setCreateTime(now);
            int rows = taskPersistence.batchSave(Collections.singletonList(task));
            return rows > 0;
        } catch (SQLException e) {
            log.error("Task add failed!", e);
            return false;
        }
    }

    public Integer batchAdd(List<Task> taskList) {
        try {
            int now = DateUtil.now();
            taskList.forEach(t -> {
                t.setUpdateTime(now);
                t.setCreateTime(now);
            });

            return taskPersistence.batchSave(taskList);
        } catch (SQLException e) {
            log.error("Task add failed!", e);
            return 0;
        }
    }

    public Task getByTaskId(String taskId) {
        try {
            return taskPersistence.findByTaskId(taskId);
        } catch (SQLException e) {
            log.error("Task getByTaskId failed!", e);
            return null;
        }
    }

    public Integer batchDeleteByTaskIds(List<String> taskIds) {
        try {
            return taskPersistence.batchDeleteByTaskIds(taskIds);
        } catch (SQLException e) {
            log.error("Task batchDeleteByTaskIds failed!", e);
            throw new RuntimeException(e);
        }
    }


    public Integer batchUpdateStatusByTaskId(List<Task> tasks, Integer currentStatus) {
        try {
            return taskPersistence.batchUpdateStatusByTaskId(tasks, currentStatus);
        } catch (SQLException e) {
            log.error("Task batchUpdateStatusByTaskId failed!", e);
            throw new RuntimeException(e);
        }
    }

    public List<Task> getList(Long instanceId, Long circleId, Long size) {
        try {
            return taskPersistence.findListByPageSize(instanceId, circleId, size);
        } catch (SQLException e) {
            log.error("Task getList failed!", e);
            throw new RuntimeException(e);
        }
    }


    public Integer countTask(Long instanceId, Long circleId, List<Integer> statusList) {
        try {
            return taskPersistence.countTask(instanceId, circleId, statusList);
        } catch (SQLException e) {
            log.error("Task countTask failed!", e);
            return 0;
        }
    }
}
