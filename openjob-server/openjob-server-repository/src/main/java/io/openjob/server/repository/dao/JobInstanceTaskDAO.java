package io.openjob.server.repository.dao;

import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.dto.TaskGroupCountDTO;
import io.openjob.server.repository.entity.JobInstanceTask;
import org.springframework.expression.spel.ast.Literal;
import scala.Int;

import java.util.List;

/**
 * @author stelin swoft@qq.com
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
     * Get by task id
     *
     * @param taskId taskId
     * @return JobInstanceTask
     */
    JobInstanceTask getByTaskId(String taskId);

    /**
     * Batch save.
     *
     * @param taskList taskList
     * @return Integer
     */
    Integer batchSave(List<JobInstanceTask> taskList);

    /**
     * Get latest parent task
     *
     * @param instanceId   instanceId
     * @param parentTaskId parentTaskId
     * @return JobInstanceTask
     */
    JobInstanceTask getLatestParentTask(Long instanceId, String parentTaskId);

    /**
     * Get circle list
     *
     * @param instanceId instanceId
     * @param page       page
     * @param size       size
     * @return PageDTO
     */
    PageDTO<JobInstanceTask> getCircleList(Long instanceId, Integer page, Integer size);

    /**
     * Get child list
     *
     * @param parentTaskId parentTaskId
     * @param page         page
     * @param size         size
     * @return return
     */
    PageDTO<JobInstanceTask> getChildList(String parentTaskId, Integer page, Integer size);

    /**
     * Get task list
     *
     * @param jobInstanceId   jobInstanceId
     * @param dispatchVersion dispatchVersion
     * @param page            page
     * @param size            size
     * @return PageDTO
     */
    PageDTO<JobInstanceTask> getTaskList(Long jobInstanceId, Long dispatchVersion, Integer page, Integer size);

    /**
     * Get mr task list
     *
     * @param jobInstanceId   jobInstanceId
     * @param dispatchVersion dispatchVersion
     * @param taskNames       taskNames
     * @param page            page
     * @param size            size
     * @return PageDTO
     */
    PageDTO<JobInstanceTask> getMrTaskList(Long jobInstanceId, Long dispatchVersion, List<String> taskNames, Integer page, Integer size);

    /**
     * Count by parent task id
     *
     * @param parentTaskIds parentTaskIds
     * @return List
     */
    List<TaskGroupCountDTO> countByParentTaskIds(List<String> parentTaskIds);
}
