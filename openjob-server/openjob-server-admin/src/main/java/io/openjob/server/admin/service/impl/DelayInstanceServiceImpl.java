package io.openjob.server.admin.service.impl;

import io.openjob.common.constant.CommonConstant;
import io.openjob.server.admin.request.delay.ListDelayInstanceRequest;
import io.openjob.server.admin.request.job.DeleteDelayInstanceVO;
import io.openjob.server.admin.service.DelayInstanceService;
import io.openjob.server.admin.vo.delay.ListDelayInstanceVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.ObjectUtil;
import io.openjob.server.common.util.PageUtil;
import io.openjob.server.common.vo.PageVO;
import io.openjob.server.repository.dao.DelayInstanceDAO;
import io.openjob.server.repository.dto.DelayInstancePageDTO;
import io.openjob.server.repository.entity.DelayInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Service
public class DelayInstanceServiceImpl implements DelayInstanceService {
    private final DelayInstanceDAO delayInstanceDAO;

    @Autowired
    public DelayInstanceServiceImpl(DelayInstanceDAO delayInstanceDAO) {
        this.delayInstanceDAO = delayInstanceDAO;
    }

    @Override
    public PageVO<ListDelayInstanceVO> pageList(ListDelayInstanceRequest request) {
        DelayInstancePageDTO delayInstancePageDTO = ObjectUtil.mapObject(request, DelayInstancePageDTO.class);
        PageDTO<DelayInstance> pageDTO = this.delayInstanceDAO.pageList(delayInstancePageDTO);
        return PageUtil.convert(pageDTO, ds -> ObjectUtil.mapObject(ds, ListDelayInstanceVO.class));
    }

    @Override
    public DeleteDelayInstanceVO delete(DeleteDelayInstanceVO request) {
        this.delayInstanceDAO.updateDeleted(request.getId(), CommonConstant.YES);
        return new DeleteDelayInstanceVO();
    }
}
