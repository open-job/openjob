package io.openjob.server.repository.repository;

import io.openjob.server.repository.dto.GroupCountDTO;
import io.openjob.server.repository.dto.TaskGroupCountDTO;
import io.openjob.server.repository.entity.JobInstanceTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public interface JobInstanceTaskRepository extends JpaRepository<JobInstanceTask, Long> {

    /**
     * Find first  job instance task
     *
     * @return JobInstanceTask
     */
    JobInstanceTask findFirstByJobInstanceIdAndParentTaskIdOrderByCircleIdDesc(Long instanceId, String parentTaskId);


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
