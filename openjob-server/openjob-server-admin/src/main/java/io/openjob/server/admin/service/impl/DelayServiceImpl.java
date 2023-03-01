package io.openjob.server.admin.service.impl;

import io.openjob.common.constant.CommonConstant;
import io.openjob.server.admin.request.delay.AddDelayRequest;
import io.openjob.server.admin.request.delay.DeleteDelayRequest;
import io.openjob.server.admin.request.delay.ListDelayRequest;
import io.openjob.server.admin.request.delay.UpdateDelayRequest;
import io.openjob.server.admin.request.delay.UpdateStatusDelayRequest;
import io.openjob.server.admin.service.DelayService;
import io.openjob.server.admin.vo.delay.AddDelayVO;
import io.openjob.server.admin.vo.delay.DeleteDelayVO;
import io.openjob.server.admin.vo.delay.ListDelayVO;
import io.openjob.server.admin.vo.delay.UpdateDelayVO;
import io.openjob.server.admin.vo.delay.UpdateStatusDelayVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.ObjectUtil;
import io.openjob.server.common.util.PageUtil;
import io.openjob.server.common.vo.PageVO;
import io.openjob.server.repository.dao.DelayDAO;
import io.openjob.server.repository.dto.DelayPageDTO;
import io.openjob.server.repository.entity.Delay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Service
public class DelayServiceImpl implements DelayService {
    private final DelayDAO delayDAO;

    @Autowired
    public DelayServiceImpl(DelayDAO delayDAO) {
        this.delayDAO = delayDAO;
    }

    @Override
    public AddDelayVO add(AddDelayRequest addRequest) {
        Delay delay = ObjectUtil.mapObject(addRequest, Delay.class);
        this.delayDAO.save(delay);
        return new AddDelayVO();
    }

    @Override
    public PageVO<ListDelayVO> list(ListDelayRequest listDelayRequest) {
        DelayPageDTO delayPageDTO = ObjectUtil.mapObject(listDelayRequest, DelayPageDTO.class);
        PageDTO<Delay> pageList = this.delayDAO.pageList(delayPageDTO);
        return PageUtil.convert(pageList, d -> ObjectUtil.mapObject(d, ListDelayVO.class));
    }

    @Override
    public DeleteDelayVO delete(DeleteDelayRequest deleteDelayRequest) {
        this.delayDAO.updateStatusOrDeleted(deleteDelayRequest.getId(), null, CommonConstant.YES);
        return new DeleteDelayVO();
    }

    @Override
    public UpdateDelayVO update(UpdateDelayRequest updateDelayRequest) {
        Delay delay = ObjectUtil.mapObject(updateDelayRequest, Delay.class);
        this.delayDAO.update(delay);
        return new UpdateDelayVO();
    }

    @Override
    public UpdateStatusDelayVO updateStatus(UpdateStatusDelayRequest updateRequest) {
        this.delayDAO.updateStatusOrDeleted(updateRequest.getId(), updateRequest.getStatus(), null);
        return new UpdateStatusDelayVO();
    }
}
