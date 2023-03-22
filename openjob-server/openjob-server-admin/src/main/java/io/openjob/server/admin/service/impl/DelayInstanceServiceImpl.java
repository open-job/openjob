package io.openjob.server.admin.service.impl;

import com.google.common.collect.Lists;
import io.openjob.common.constant.CommonConstant;
import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.server.admin.request.delay.DeleteDelayInstanceRequest;
import io.openjob.server.admin.request.delay.ListDelayInstanceLogRequest;
import io.openjob.server.admin.request.delay.ListDelayInstanceRequest;
import io.openjob.server.admin.request.delay.StopDelayInstanceRequest;
import io.openjob.server.admin.service.DelayInstanceService;
import io.openjob.server.admin.util.LogFormatUtil;
import io.openjob.server.admin.vo.delay.DeleteDelayInstanceVO;
import io.openjob.server.admin.vo.delay.ListDelayInstanceLogVO;
import io.openjob.server.admin.vo.delay.ListDelayInstanceVO;
import io.openjob.server.admin.vo.delay.StopDelayInstanceVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.common.util.PageUtil;
import io.openjob.server.common.vo.PageVO;
import io.openjob.server.log.dao.LogDAO;
import io.openjob.server.log.dto.ProcessorLog;
import io.openjob.server.repository.dao.DelayInstanceDAO;
import io.openjob.server.repository.dto.DelayInstancePageDTO;
import io.openjob.server.repository.entity.DelayInstance;
import io.openjob.server.scheduler.dto.DelayInstanceDeleteRequestDTO;
import io.openjob.server.scheduler.dto.DelayInstanceStopRequestDTO;
import io.openjob.server.scheduler.scheduler.DelayInstanceScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Service
public class DelayInstanceServiceImpl implements DelayInstanceService {
    private final LogDAO logDAO;
    private final DelayInstanceDAO delayInstanceDAO;
    private final DelayInstanceScheduler delayInstanceScheduler;

    @Autowired
    public DelayInstanceServiceImpl(LogDAO logDAO, DelayInstanceDAO delayInstanceDAO, DelayInstanceScheduler delayInstanceScheduler) {
        this.logDAO = logDAO;
        this.delayInstanceDAO = delayInstanceDAO;
        this.delayInstanceScheduler = delayInstanceScheduler;
    }

    @Override
    public PageVO<ListDelayInstanceVO> pageList(ListDelayInstanceRequest request) {
        DelayInstancePageDTO delayInstancePageDTO = BeanMapperUtil.map(request, DelayInstancePageDTO.class);
        PageDTO<DelayInstance> pageDTO = this.delayInstanceDAO.pageList(delayInstancePageDTO);

        return PageUtil.convert(pageDTO, ds -> BeanMapperUtil.map(ds, ListDelayInstanceVO.class));
    }

    @Override
    public DeleteDelayInstanceVO delete(DeleteDelayInstanceRequest request) {
        DelayInstanceDeleteRequestDTO delayInstanceDeleteRequestDTO = new DelayInstanceDeleteRequestDTO();
        delayInstanceDeleteRequestDTO.setTaskId(request.getTaskId());
        this.delayInstanceScheduler.delete(delayInstanceDeleteRequestDTO);

        // Update deleted
        this.delayInstanceDAO.updateDeleted(request.getTaskId(), CommonConstant.YES);
        return new DeleteDelayInstanceVO();
    }

    @Override
    public StopDelayInstanceVO stop(StopDelayInstanceRequest request) {
        DelayInstanceStopRequestDTO delayInstanceStopRequestDTO = new DelayInstanceStopRequestDTO();
        delayInstanceStopRequestDTO.setTaskId(request.getTaskId());
        this.delayInstanceScheduler.stop(delayInstanceStopRequestDTO);

        // Update status
        return new StopDelayInstanceVO();
    }

    @Override
    public ListDelayInstanceLogVO listProcessorLog(ListDelayInstanceLogRequest request) {
        List<String> list = Lists.newArrayList();

        AtomicLong nextTime = new AtomicLong(0L);
        Integer isComplete = CommonConstant.NO;
        try {
            List<ProcessorLog> processorLogs = this.logDAO.queryByPage(request.getTaskId(), request.getTime(), request.getSize());

            if (!CollectionUtils.isEmpty(processorLogs)) {
                // Processor list and nextTime.
                processorLogs.forEach(l -> list.add(LogFormatUtil.formatLog(l)));
                nextTime.set(processorLogs.get(processorLogs.size() - 1).getTime());
            } else {
                boolean completeStatus = TaskStatusEnum.FINISH_LIST.contains(request.getStatus());
                if (completeStatus) {
                    isComplete = CommonConstant.YES;
                } else if (CommonConstant.YES.equals(request.getLoading())) {
                    DelayInstance delayInstance = this.delayInstanceDAO.getByTaskId(request.getTaskId());
                    if (TaskStatusEnum.FINISH_LIST.contains(delayInstance.getStatus())) {
                        isComplete = CommonConstant.YES;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ListDelayInstanceLogVO listInstanceVO = new ListDelayInstanceLogVO();
        listInstanceVO.setList(list);
        listInstanceVO.setTime(nextTime.get());
        listInstanceVO.setComplete(isComplete);
        return listInstanceVO;
    }
}
