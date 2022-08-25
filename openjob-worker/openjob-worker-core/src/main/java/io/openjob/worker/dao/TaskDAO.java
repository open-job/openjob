package io.openjob.worker.dao;

import io.openjob.common.util.DateUtil;
import io.openjob.worker.entity.Task;
import io.openjob.worker.persistence.H2MemoryPersistence;
import io.openjob.worker.persistence.TaskPersistence;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import scala.Int;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
public class TaskDAO {
    public final static TaskDAO INSTANCE = new TaskDAO();
    private final TaskPersistence taskPersistence;

    private TaskDAO() {
        this.taskPersistence = new H2MemoryPersistence();
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
            return 0;
        }
    }


    public Integer batchUpdateStatusByTaskId(List<Task> tasks) {
        try {
            return taskPersistence.batchUpdateStatusByTaskId(tasks);
        } catch (SQLException e) {
            log.error("Task batchUpdateStatusByTaskId failed!", e);
            return 0;
        }
    }

    public Integer countTask(Long instanceId, List<Integer> statusList) {
        try {
            return taskPersistence.countTask(instanceId, statusList);
        } catch (SQLException e) {
            log.error("Task countTask failed!", e);
            return 0;
        }
    }
}
