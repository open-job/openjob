package io.openjob.server.admin.service.impl;

import com.google.common.collect.Lists;
import io.openjob.common.constant.CommonConstant;
import io.openjob.common.constant.ExecuteTypeEnum;
import io.openjob.common.constant.TaskConstant;
import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.server.admin.request.task.ListChildTaskRequest;
import io.openjob.server.admin.request.task.ListSecondRequest;
import io.openjob.server.admin.request.task.ListTaskLogRequest;
import io.openjob.server.admin.request.task.ListTaskRequest;
import io.openjob.server.admin.service.JobInstanceTaskService;
import io.openjob.server.admin.util.LogFormatUtil;
import io.openjob.server.admin.vo.task.ListChildTaskVO;
import io.openjob.server.admin.vo.task.ListSecondVO;
import io.openjob.server.admin.vo.task.ListTaskLogVO;
import io.openjob.server.admin.vo.task.ListTaskVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.common.util.PageUtil;
import io.openjob.server.common.vo.PageVO;
import io.openjob.server.log.dao.LogDAO;
import io.openjob.server.log.dto.ProcessorLogDTO;
import io.openjob.server.repository.dao.JobInstanceTaskDAO;
import io.openjob.server.repository.dto.TaskGroupCountDTO;
import io.openjob.server.repository.entity.DelayInstance;
import io.openjob.server.repository.entity.JobInstanceTask;
import org.codehaus.janino.IClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.7
 */
@Service
public class JobInstanceTaskServiceImpl implements JobInstanceTaskService {
    private final JobInstanceTaskDAO jobInstanceTaskDAO;
    private final LogDAO logDAO;

    @Autowired
    public JobInstanceTaskServiceImpl(JobInstanceTaskDAO jobInstanceTaskDAO, LogDAO logDAO) {
        this.jobInstanceTaskDAO = jobInstanceTaskDAO;
        this.logDAO = logDAO;
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

    @Override
    public PageVO<ListTaskVO> getTaskList(ListTaskRequest request) {
        // Second delay
        if (ExecuteTypeEnum.isMapReduce(request.getExecuteType())) {
            return this.getMrTaskList(request);
        }

        PageDTO<JobInstanceTask> pageDTO = this.jobInstanceTaskDAO.getTaskList(request.getJobInstanceId(), request.getDispatchVersion(), request.getPage(), request.getSize());
        return PageUtil.convert(pageDTO, t -> {
            ListTaskVO listTaskVO = BeanMapperUtil.map(t, ListTaskVO.class);
            listTaskVO.setChildCount(1L);
            return listTaskVO;
        });
    }

    @Override
    public ListTaskLogVO getTaskLogList(ListTaskLogRequest request) {
        List<String> list = new ArrayList<>();
        AtomicLong nextTime = new AtomicLong(0L);
        Integer isComplete = CommonConstant.NO;
        try {
            List<ProcessorLogDTO> processorLogs = this.logDAO.queryByScroll(request.getTaskId(), request.getTime(), request.getSize());

            if (!CollectionUtils.isEmpty(processorLogs)) {
                // Processor list and nextTime.
                processorLogs.forEach(l -> list.add(LogFormatUtil.formatLog(l)));
                nextTime.set(processorLogs.get(processorLogs.size() - 1).getTime());
            } else {
                boolean completeStatus = TaskStatusEnum.FINISH_LIST.contains(request.getStatus());
                if (completeStatus) {
                    isComplete = CommonConstant.YES;
                } else if (CommonConstant.YES.equals(request.getLoading())) {
                    JobInstanceTask jobInstanceTask = this.jobInstanceTaskDAO.getByTaskId(request.getTaskId());
                    if (TaskStatusEnum.FINISH_LIST.contains(jobInstanceTask.getStatus())) {
                        isComplete = CommonConstant.YES;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ListTaskLogVO listTaskLogVO = new ListTaskLogVO();
        listTaskLogVO.setList(list);
        listTaskLogVO.setTime(nextTime.get());
        listTaskLogVO.setComplete(isComplete);
        return listTaskLogVO;
    }

    private PageVO<ListTaskVO> getMrTaskList(ListTaskRequest request) {
        List<String> taskNames = Arrays.asList(TaskConstant.MAP_TASK_ROOT_NAME, TaskConstant.MAP_TASK_REDUCE_NAME);
        PageDTO<JobInstanceTask> pageDTO = this.jobInstanceTaskDAO.getMrTaskList(request.getJobInstanceId(), request.getDispatchVersion(), taskNames, request.getPage(), request.getSize());

        return PageUtil.convert(pageDTO, t -> {
            ListTaskVO listTaskVO = BeanMapperUtil.map(t, ListTaskVO.class);
            listTaskVO.setChildCount(1L);
            return listTaskVO;
        });
    }
}
