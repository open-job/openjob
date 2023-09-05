package io.openjob.server.repository.repository;

import io.openjob.server.repository.dto.TaskGroupCountDTO;
import io.openjob.server.repository.entity.JobInstanceTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public interface JobInstanceTaskRepository extends JpaRepository<JobInstanceTask, Long> {

    /**
     * Find by task id
     *
     * @param taskId taskId
     * @return JobInstanceTask
     */
    JobInstanceTask findByTaskId(String taskId);

    /**
     * Find first  job instance task
     *
     * @param instanceId   instanceId
     * @param parentTaskId parentTaskId
     * @return JobInstanceTask
     */
    JobInstanceTask findFirstByJobInstanceIdAndParentTaskIdOrderByCircleIdDesc(Long instanceId, String parentTaskId);

    /**
     * Find mr task list
     *
     * @param jobInstanceId   jobInstanceId
     * @param dispatchVersion dispatchVersion
     * @param taskNames       taskNames
     * @param pageable        pageable
     * @return Page
     */
    @Query(value = "SELECT jit FROM JobInstanceTask as jit where jit.jobInstanceId=?1 and jit.dispatchVersion=?2 and jit.taskName in ?3")
    Page<JobInstanceTask> findMrTaskList(Long jobInstanceId, Long dispatchVersion, List<String> taskNames, Pageable pageable);

    /**
     * Group by date time
     *
     * @param parentTaskId parentTaskId
     * @return List
     */
    @Query(value = "SELECT new io.openjob.server.repository.dto.TaskGroupCountDTO(j.parentTaskId, count(j.id)) from JobInstanceTask as j "
            + "where j.parentTaskId in ?1 GROUP BY j.parentTaskId ")
    List<TaskGroupCountDTO> countByParentTaskIds(List<String> parentTaskId);
}
