package io.openjob.worker.dao;

import io.openjob.worker.entity.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class TaskDAOTest {
    @Test
    public void testAdd() {
        String taskId = String.valueOf(UUID.randomUUID());

        Task task = new Task();
        task.setJobId(1L);
        task.setInstanceId(1L);
        task.setCircleId(0L);
        task.setTaskId(taskId);
        task.setTaskName("ROOT");
        task.setTaskParentId(String.valueOf(0));
        task.setStatus(1);
        task.setWorkerAddress("");

        Boolean result = TaskDAO.INSTANCE.add(task);
        Assertions.assertTrue(result);

        Task queryTask = TaskDAO.INSTANCE.getByTaskId(taskId);
        Assertions.assertEquals(taskId, queryTask.getTaskId());
    }
}
