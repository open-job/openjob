package io.openjob.worker.dao;

import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.worker.entity.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class TaskDAOTest {
    @Test
    public void testCommon() {
        String taskResult = "exception result";
        String taskId = String.valueOf(UUID.randomUUID());

        Task task = new Task();
        task.setJobId(1L);
        task.setInstanceId(1L);
        task.setCircleId(0L);
        task.setTaskId(taskId);
        task.setTaskName("ROOT");
        task.setTaskParentId(String.valueOf(0));
        task.setStatus(1);
        task.setResult(taskResult);
        task.setWorkerAddress("");

        // Add
        boolean result = TaskDAO.INSTANCE.add(task);
        Assertions.assertTrue(result);

        // Get by task id.
        Task queryTask = TaskDAO.INSTANCE.getByTaskId(taskId);
        Assertions.assertEquals(taskId, queryTask.getTaskId());
        Assertions.assertEquals(taskResult, queryTask.getResult());

        // Task tow.
        String taskId2 = String.valueOf(UUID.randomUUID());
        Task task2 = new Task();
        task2.setJobId(1L);
        task2.setInstanceId(1L);
        task2.setCircleId(0L);
        task2.setTaskId(taskId2);
        task2.setTaskName("ROOT");
        task2.setTaskParentId(String.valueOf(0));
        task2.setStatus(2);
        task2.setWorkerAddress("");
        TaskDAO.INSTANCE.add(task2);

        // Task three.
        String taskId3 = String.valueOf(UUID.randomUUID());
        Task task3 = new Task();
        task3.setJobId(1L);
        task3.setInstanceId(1L);
        task3.setCircleId(0L);
        task3.setTaskId(taskId3);
        task3.setTaskName("ROOT");
        task3.setTaskParentId(String.valueOf(0));
        task3.setStatus(3);
        task3.setWorkerAddress("");
        TaskDAO.INSTANCE.add(task3);

        // Count task.
        int count = TaskDAO.INSTANCE.countTask(1L, 0L, Arrays.asList(1, 3));
        Assertions.assertEquals(count, 2);

        int count2 = TaskDAO.INSTANCE.countTask(1L, 0L, Arrays.asList(1, 2, 3));
        Assertions.assertEquals(count2, 3);

        // Update list.
        List<Task> updateList = new ArrayList<>();
        Task ut = new Task();
        ut.setTaskId(taskId);
        ut.setStatus(6);
        updateList.add(ut);

        Task ut2 = new Task();
        ut2.setTaskId(taskId2);
        ut2.setStatus(6);
        updateList.add(ut2);

        Task ut3 = new Task();
        ut3.setTaskId(taskId3);
        ut3.setStatus(6);
        updateList.add(ut3);

        // Update by status.
        int updateRows = TaskDAO.INSTANCE.batchUpdateStatusByTaskId(updateList, TaskStatusEnum.UNKNOWN.getStatus());
        Assertions.assertEquals(updateRows, 3);

        int count3 = TaskDAO.INSTANCE.countTask(1L, 0L, Collections.singletonList(6));
        Assertions.assertEquals(count3, 3);

        // Update by task ids.
        int deleteRows = TaskDAO.INSTANCE.batchDeleteByTaskIds(Arrays.asList(taskId, taskId2, taskId3));
        Assertions.assertEquals(deleteRows, 3);
        int deleteResult = TaskDAO.INSTANCE.countTask(1L, 0L, Collections.singletonList(6));
        Assertions.assertEquals(deleteResult, 0);
    }

    @Test
    public void testGetList() {
        int testSize = 230;
        long instanceId = 12L;
        long circleId = 0L;
        List<Task> taskList = new ArrayList<>();
        for (long i = 0; i < testSize; i++) {
            Task task = new Task();
            task.setJobId(i);
            task.setInstanceId(instanceId);
            task.setCircleId(circleId);
            task.setTaskId("taskId" + i);
            task.setTaskName("ROOT");
            task.setTaskParentId(String.valueOf(0));
            task.setStatus(1);
            task.setWorkerAddress("");

            taskList.add(task);
        }

        int size = TaskDAO.INSTANCE.batchAdd(taskList);
        Assertions.assertEquals(size, testSize);

        List<Task> getList = new ArrayList<>();

        long pageSize = 23;
        long page = 1;

        List<Task> queryList;
        do {
            queryList = TaskDAO.INSTANCE.getList(instanceId, circleId, page, pageSize);
            getList.addAll(queryList);
            page++;
        } while (!queryList.isEmpty());

        Assertions.assertEquals(getList.size(), testSize);

        //Test unique task(exclude repeat task)
        Set<Long> collect = getList.stream().map(Task::getJobId).collect(Collectors.toSet());
        Assertions.assertEquals(collect.size(), testSize);
    }
}
