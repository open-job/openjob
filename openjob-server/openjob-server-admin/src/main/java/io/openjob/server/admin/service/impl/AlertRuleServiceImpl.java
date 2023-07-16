package io.openjob.server.admin.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import io.openjob.common.util.JsonUtil;
import io.openjob.server.admin.request.alert.AddAlertRuleRequest;
import io.openjob.server.admin.request.alert.DeleteAlertRuleRequest;
import io.openjob.server.admin.request.alert.ListAlertRuleRequest;
import io.openjob.server.admin.request.alert.UpdateAlertRuleRequest;
import io.openjob.server.admin.request.alert.UpdateAlertRuleStatusRequest;
import io.openjob.server.admin.service.AlertRuleService;
import io.openjob.server.admin.vo.alert.AddAlertRuleVO;
import io.openjob.server.admin.vo.alert.DeleteAlertRuleVO;
import io.openjob.server.admin.vo.alert.ListAlertRuleVO;
import io.openjob.server.admin.vo.alert.UpdateAlertRuleStatusVO;
import io.openjob.server.admin.vo.alert.UpdateAlertRuleVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.common.util.PageUtil;
import io.openjob.server.common.vo.PageVO;
import io.openjob.server.repository.dao.AlertRuleDAO;
import io.openjob.server.repository.entity.AlertRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.5
 */
@Service
public class AlertRuleServiceImpl implements AlertRuleService {
    private final AlertRuleDAO alertRuleDAO;

    @Autowired
    public AlertRuleServiceImpl(AlertRuleDAO alertRuleDAO) {
        this.alertRuleDAO = alertRuleDAO;
    }

    @Override
    public AddAlertRuleVO add(AddAlertRuleRequest request) {
        AlertRule alertRule = BeanMapperUtil.map(request, AlertRule.class);
        alertRule.setNamespaceAppIds(JsonUtil.encode(request.getNamespaceAppIds()));
        alertRule.setEvents(JsonUtil.encode(request.getEvents()));
        alertRule.setMetrics(JsonUtil.encode(request.getMetrics()));

        this.alertRuleDAO.save(alertRule);
        return new AddAlertRuleVO();
    }

    @Override
    public DeleteAlertRuleVO delete(DeleteAlertRuleRequest request) {
        this.alertRuleDAO.deleteById(request.getId());
        return new DeleteAlertRuleVO();
    }

    @Override
    public UpdateAlertRuleVO update(UpdateAlertRuleRequest request) {
        AlertRule alertRule = BeanMapperUtil.map(request, AlertRule.class);
        alertRule.setNamespaceAppIds(JsonUtil.encode(request.getNamespaceAppIds()));
        alertRule.setEvents(JsonUtil.encode(request.getEvents()));
        alertRule.setMetrics(JsonUtil.encode(request.getMetrics()));

        this.alertRuleDAO.update(alertRule);
        return new UpdateAlertRuleVO();
    }

    @Override
    public UpdateAlertRuleStatusVO updateStatus(UpdateAlertRuleStatusRequest request) {
        this.alertRuleDAO.updateStatus(request.getId(), request.getStatus());
        return new UpdateAlertRuleStatusVO();
    }

    @Override
    public PageVO<ListAlertRuleVO> list(ListAlertRuleRequest request) {
        PageDTO<AlertRule> pageList = this.alertRuleDAO.pageList(request.getName(), request.getPage(), request.getSize());
        if (CollectionUtils.isEmpty(pageList.getList())) {
            return PageUtil.emptyList(ListAlertRuleVO.class);
        }

        return PageUtil.convert(pageList, r -> {
            ListAlertRuleVO listAlertRuleVO = BeanMapperUtil.map(r, ListAlertRuleVO.class);

            // App ids
            listAlertRuleVO.setNamespaceAppIds(JsonUtil.decode(r.getNamespaceAppIds(), new TypeReference<List<String>>() {
            }));

            // Events
            listAlertRuleVO.setEvents(JsonUtil.decode(r.getEvents(), new TypeReference<List<String>>() {
            }));

            // Metrics
            listAlertRuleVO.setMetrics(JsonUtil.decode(r.getMetrics(), new TypeReference<List<ListAlertRuleVO.AlertRuleMetricsVO>>() {
            }));
            return listAlertRuleVO;
        });
    }
}
