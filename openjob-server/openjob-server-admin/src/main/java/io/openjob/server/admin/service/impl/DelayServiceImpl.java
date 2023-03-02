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
import io.openjob.server.repository.dao.AppDAO;
import io.openjob.server.repository.dao.DelayDAO;
import io.openjob.server.repository.dto.DelayPageDTO;
import io.openjob.server.repository.entity.App;
import io.openjob.server.repository.entity.Delay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Service
public class DelayServiceImpl implements DelayService {
    private final DelayDAO delayDAO;
    private final AppDAO appDAO;

    @Autowired
    public DelayServiceImpl(DelayDAO delayDAO, AppDAO appDAO) {
        this.delayDAO = delayDAO;
        this.appDAO = appDAO;
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
        if (CollectionUtils.isEmpty(pageList.getList())) {
            return PageUtil.emptyList(ListDelayVO.class);
        }

        // App list.
        List<Long> appIds = pageList.getList().stream()
                .map(Delay::getAppId).distinct().collect(Collectors.toList());
        Map<Long, App> appMap = this.appDAO.getByIds(appIds).stream()
                .collect(Collectors.toMap(App::getId, a -> a));

        // Page
        return PageUtil.convert(pageList, d -> {
            ListDelayVO listDelayVO = ObjectUtil.mapObject(d, ListDelayVO.class);
            App app = appMap.get(d.getAppId());
            if (Objects.nonNull(app)) {
                listDelayVO.setAppName(app.getName());
            }
            return listDelayVO;
        });
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
