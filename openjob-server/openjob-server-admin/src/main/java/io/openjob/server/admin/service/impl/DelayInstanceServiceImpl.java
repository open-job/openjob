package io.openjob.server.admin.service.impl;

import io.openjob.common.constant.CommonConstant;
import io.openjob.server.admin.request.delay.DeleteDelayRequest;
import io.openjob.server.admin.request.delay.ListDelayInstanceRequest;
import io.openjob.server.admin.vo.delay.DeleteDelayInstanceVO;
import io.openjob.server.admin.service.DelayInstanceService;
import io.openjob.server.admin.vo.delay.ListDelayInstanceVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.common.util.PageUtil;
import io.openjob.server.common.vo.PageVO;
import io.openjob.server.repository.dao.DelayInstanceDAO;
import io.openjob.server.repository.dto.DelayInstancePageDTO;
import io.openjob.server.repository.entity.DelayInstance;
import io.openjob.server.scheduler.dto.DelayInstanceDeleteRequestDTO;
import io.openjob.server.scheduler.scheduler.DelayInstanceScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Service
public class DelayInstanceServiceImpl implements DelayInstanceService {
    private final DelayInstanceDAO delayInstanceDAO;
    private final DelayInstanceScheduler delayInstanceScheduler;

    @Autowired
    public DelayInstanceServiceImpl(DelayInstanceDAO delayInstanceDAO, DelayInstanceScheduler delayInstanceScheduler) {
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
    public DeleteDelayInstanceVO delete(DeleteDelayRequest request) {
        DelayInstanceDeleteRequestDTO delayInstanceDeleteRequestDTO = new DelayInstanceDeleteRequestDTO();
        delayInstanceDeleteRequestDTO.setTaskId(request.getTaskId());

        this.delayInstanceScheduler.delete(delayInstanceDeleteRequestDTO);
        this.delayInstanceDAO.updateDeleted(request.getTaskId(), CommonConstant.YES);
        return new DeleteDelayInstanceVO();
    }
}
