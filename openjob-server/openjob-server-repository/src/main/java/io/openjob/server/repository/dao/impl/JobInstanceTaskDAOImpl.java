package io.openjob.server.repository.dao.impl;

import io.openjob.common.constant.TaskConstant;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.dao.JobInstanceTaskDAO;
import io.openjob.server.repository.dto.TaskGroupCountDTO;
import io.openjob.server.repository.entity.JobInstanceTask;
import io.openjob.server.repository.repository.JobInstanceTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Component
public class JobInstanceTaskDAOImpl implements JobInstanceTaskDAO {
    private final JobInstanceTaskRepository jobInstanceTaskRepository;

    @Autowired
    public JobInstanceTaskDAOImpl(JobInstanceTaskRepository jobInstanceTaskRepository) {
        this.jobInstanceTaskRepository = jobInstanceTaskRepository;
    }

    @Override
    public Long save(JobInstanceTask jobInstanceTask) {
        return this.jobInstanceTaskRepository.save(jobInstanceTask).getId();
    }

    @Override
    public JobInstanceTask getByTaskId(String taskId) {
        return this.jobInstanceTaskRepository.findByTaskId(taskId);
    }

    @Override
    public Integer batchSave(List<JobInstanceTask> taskList) {
        return this.jobInstanceTaskRepository.saveAll(taskList).size();
    }

    @Override
    public JobInstanceTask getLatestParentTask(Long instanceId, String parentTaskId) {
        return this.jobInstanceTaskRepository.findFirstByJobInstanceIdAndParentTaskIdOrderByCircleIdDesc(instanceId, parentTaskId);
    }

    @Override
    public PageDTO<JobInstanceTask> getTaskList(Long instanceId, Integer page, Integer size) {
        JobInstanceTask jobInstanceTask = new JobInstanceTask();
        jobInstanceTask.setJobInstanceId(instanceId);
        jobInstanceTask.setParentTaskId(TaskConstant.DEFAULT_PARENT_ID);

        // Pagination
        PageDTO<JobInstanceTask> pageDTO = new PageDTO<>();
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "circleId"));

        // Page query
        this.pageQuery(page, size, jobInstanceTask, pageDTO, pageRequest);
        return pageDTO;
    }

    @Override
    public PageDTO<JobInstanceTask> getChildList(String parentTaskId, Integer page, Integer size) {
        JobInstanceTask jobInstanceTask = new JobInstanceTask();
        jobInstanceTask.setParentTaskId(parentTaskId);

        // Pagination
        PageDTO<JobInstanceTask> pageDTO = new PageDTO<>();
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "id"));

        // Page query
        this.pageQuery(page, size, jobInstanceTask, pageDTO, pageRequest);
        return pageDTO;
    }

    @Override
    public PageDTO<JobInstanceTask> getTaskListByDispatchVersion(Long jobInstanceId, Long dispatchVersion, Integer page, Integer size) {
        JobInstanceTask jobInstanceTask = new JobInstanceTask();
        jobInstanceTask.setJobInstanceId(jobInstanceId);
        jobInstanceTask.setDispatchVersion(dispatchVersion);

        // Pagination
        PageDTO<JobInstanceTask> pageDTO = new PageDTO<>();
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "id"));

        // Page query
        this.pageQuery(page, size, jobInstanceTask, pageDTO, pageRequest);
        return pageDTO;
    }

    @Override
    public PageDTO<JobInstanceTask> getMrTaskList(Long jobInstanceId, Long dispatchVersion, List<String> taskNames, Integer page, Integer size) {
        // Pagination
        PageDTO<JobInstanceTask> pageDTO = new PageDTO<>();
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "id"));

        Page<JobInstanceTask> pageList = this.jobInstanceTaskRepository.findMrTaskList(jobInstanceId, dispatchVersion, taskNames, pageRequest);
        if (!pageList.isEmpty()) {
            pageDTO.setPage(page);
            pageDTO.setSize(size);
            pageDTO.setTotal(pageList.getTotalElements());
            pageDTO.setList(pageList.toList());
        }
        return pageDTO;
    }

    @Override
    public List<TaskGroupCountDTO> countByParentTaskIds(List<String> parentTaskIds) {
        return this.jobInstanceTaskRepository.countByParentTaskIds(parentTaskIds);
    }

    private void pageQuery(Integer page, Integer size, JobInstanceTask jobInstanceTask, PageDTO<JobInstanceTask> pageDTO, PageRequest pageRequest) {
        Page<JobInstanceTask> pageList = this.jobInstanceTaskRepository.findAll(Example.of(jobInstanceTask), pageRequest);
        if (!pageList.isEmpty()) {
            pageDTO.setPage(page);
            pageDTO.setSize(size);
            pageDTO.setTotal(pageList.getTotalElements());
            pageDTO.setList(pageList.toList());
        }
    }
}
