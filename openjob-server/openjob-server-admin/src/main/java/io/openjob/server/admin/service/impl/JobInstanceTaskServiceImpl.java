package io.openjob.server.admin.service.impl;

import io.openjob.server.admin.request.task.ListChildTaskRequest;
import io.openjob.server.admin.request.task.ListSecondRequest;
import io.openjob.server.admin.service.JobInstanceTaskService;
import io.openjob.server.admin.vo.task.ListChildTaskVO;
import io.openjob.server.admin.vo.task.ListSecondVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.common.util.PageUtil;
import io.openjob.server.common.vo.PageVO;
import io.openjob.server.repository.dao.JobInstanceTaskDAO;
import io.openjob.server.repository.dto.TaskGroupCountDTO;
import io.openjob.server.repository.entity.JobInstanceTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.7
 */
@Service
public class JobInstanceTaskServiceImpl implements JobInstanceTaskService {
    private final JobInstanceTaskDAO jobInstanceTaskDAO;

    @Autowired
    public JobInstanceTaskServiceImpl(JobInstanceTaskDAO jobInstanceTaskDAO) {
        this.jobInstanceTaskDAO = jobInstanceTaskDAO;
    }

    @Override
    public PageVO<ListSecondVO> getCircleList(ListSecondRequest request) {
        PageDTO<JobInstanceTask> pageDTO = this.jobInstanceTaskDAO.getCircleList(request.getJobInstanceId(), request.getPage(), request.getSize());

        // Empty
        if (CollectionUtils.isEmpty(pageDTO.getList())) {
            return PageUtil.empty(pageDTO);
        }

        return PageUtil.convert(pageDTO, t -> {
            ListSecondVO listSecondVO = BeanMapperUtil.map(t, ListSecondVO.class);
            listSecondVO.setChildCount(1L);
            return listSecondVO;
        });
    }

    @Override
    public PageVO<ListChildTaskVO> getChildList(ListChildTaskRequest request) {
        PageDTO<JobInstanceTask> pageDTO = this.jobInstanceTaskDAO.getChildList(request.getTaskId(), request.getPage(), request.getSize());

        // Empty
        if (CollectionUtils.isEmpty(pageDTO.getList())) {
            return PageUtil.empty(pageDTO);
        }

        List<String> taskIds = pageDTO.getList().stream().map(JobInstanceTask::getTaskId).collect(Collectors.toList());
        Map<String, Long> countMap = Optional.ofNullable(this.jobInstanceTaskDAO.countByParentTaskIds(taskIds)).orElseGet(ArrayList::new)
                .stream().collect(Collectors.toMap(TaskGroupCountDTO::getParentTaskId, TaskGroupCountDTO::getCount));

        return PageUtil.convert(pageDTO, t -> {
            ListChildTaskVO listSecondVO = BeanMapperUtil.map(t, ListChildTaskVO.class);
            listSecondVO.setChildCount(Optional.ofNullable(countMap.get(t.getTaskId())).orElse(0L));
            return listSecondVO;
        });
    }
}
