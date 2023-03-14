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
import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.common.util.PageUtil;
import io.openjob.server.common.vo.PageVO;
import io.openjob.server.repository.dao.AppDAO;
import io.openjob.server.repository.dao.DelayDAO;
import io.openjob.server.repository.dto.DelayPageDTO;
import io.openjob.server.repository.entity.App;
import io.openjob.server.repository.entity.Delay;
import io.openjob.server.scheduler.dto.TopicTotalAndReadyCounterDTO;
import io.openjob.server.scheduler.scheduler.DelayInstanceScheduler;
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
    private final DelayInstanceScheduler delayInstanceScheduler;

    @Autowired
    public DelayServiceImpl(DelayDAO delayDAO, AppDAO appDAO, DelayInstanceScheduler delayInstanceScheduler) {
        this.delayDAO = delayDAO;
        this.appDAO = appDAO;
        this.delayInstanceScheduler = delayInstanceScheduler;
    }

    @Override
    public AddDelayVO add(AddDelayRequest addRequest) {
        Delay delay = BeanMapperUtil.map(addRequest, Delay.class);
        this.delayDAO.save(delay);
        return new AddDelayVO();
    }

    @Override
    public PageVO<ListDelayVO> list(ListDelayRequest listDelayRequest) {
        DelayPageDTO delayPageDTO = BeanMapperUtil.map(listDelayRequest, DelayPageDTO.class);
        PageDTO<Delay> pageList = this.delayDAO.pageList(delayPageDTO);
        if (CollectionUtils.isEmpty(pageList.getList())) {
            return PageUtil.emptyList(ListDelayVO.class);
        }

        // Topic ready count.
        List<String> topics = pageList.getList().stream()
                .map(Delay::getTopic).distinct().collect(Collectors.toList());
        Map<String, TopicTotalAndReadyCounterDTO> counterMap = this.delayInstanceScheduler.getTopicTotalReadyCount(topics)
                .stream()
                .collect(Collectors.toMap(TopicTotalAndReadyCounterDTO::getTopic, t -> t));

        // App list.
        List<Long> appIds = pageList.getList().stream()
                .map(Delay::getAppId).distinct().collect(Collectors.toList());
        Map<Long, App> appMap = this.appDAO.getByIds(appIds).stream()
                .collect(Collectors.toMap(App::getId, a -> a));

        // Page
        return PageUtil.convert(pageList, d -> {
            ListDelayVO listDelayVO = BeanMapperUtil.map(d, ListDelayVO.class);
            App app = appMap.get(d.getAppId());
            if (Objects.nonNull(app)) {
                listDelayVO.setAppName(app.getName());
            }

            // Total and Ready
            listDelayVO.setTotal(counterMap.get(d.getTopic()).getTotal());
            listDelayVO.setReady(counterMap.get(d.getTopic()).getReady());
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
        Delay delay = BeanMapperUtil.map(updateDelayRequest, Delay.class);
        this.delayDAO.update(delay);
        return new UpdateDelayVO();
    }

    @Override
    public UpdateStatusDelayVO updateStatus(UpdateStatusDelayRequest updateRequest) {
        this.delayDAO.updateStatusOrDeleted(updateRequest.getId(), updateRequest.getStatus(), null);
        return new UpdateStatusDelayVO();
    }
}
