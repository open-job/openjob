package io.openjob.server.repository.dao.impl;

import io.openjob.common.constant.CommonConstant;
import io.openjob.common.util.DateUtil;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.constant.AlertRuleStatusEnum;
import io.openjob.server.repository.dao.AlertRuleDAO;
import io.openjob.server.repository.entity.AlertRule;
import io.openjob.server.repository.repository.AlertRuleRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.5
 */
@Component
public class AlertRuleDAOImpl implements AlertRuleDAO {

    private final AlertRuleRepository alertRuleRepository;

    @Autowired
    public AlertRuleDAOImpl(AlertRuleRepository alertRuleRepository) {
        this.alertRuleRepository = alertRuleRepository;
    }

    @Override
    public Long save(AlertRule alertRule) {
        Long timestamp = DateUtil.timestamp();
        alertRule.setDeleted(CommonConstant.NO);
        alertRule.setDeleteTime(0L);
        alertRule.setCreateTime(timestamp);
        alertRule.setUpdateTime(timestamp);
        return this.alertRuleRepository.save(alertRule).getId();
    }

    @Override
    public void deleteById(Long id) {
        this.alertRuleRepository.deleteById(id);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        this.alertRuleRepository.findById(id)
                .ifPresent(a -> {
                    a.setStatus(status);
                    a.setUpdateTime(DateUtil.timestamp());
                    this.alertRuleRepository.save(a);
                });
    }

    @Override
    public Long update(AlertRule alertRule) {
        this.alertRuleRepository.findById(alertRule.getId())
                .ifPresent(a -> {
                    a.setName(alertRule.getName());
                    a.setNamespaceAppIds(alertRule.getNamespaceAppIds());
                    a.setEvents(alertRule.getEvents());
                    a.setMetrics(alertRule.getMetrics());
                    a.setMethod(alertRule.getMethod());
                    a.setUrl(alertRule.getUrl());
                    a.setStatus(alertRule.getStatus());
                    a.setUpdateTime(DateUtil.timestamp());
                    this.alertRuleRepository.save(a);
                });
        return alertRule.getId();
    }

    @Override
    public List<AlertRule> getEnableList() {
        AlertRule alertRule = new AlertRule();
        alertRule.setStatus(AlertRuleStatusEnum.ON.getStatus());
        return this.alertRuleRepository.findAll(Example.of(alertRule));
    }

    @Override
    public PageDTO<AlertRule> pageList(String name, Integer page, Integer size) {
        // Matcher
        ExampleMatcher matching = ExampleMatcher.matching();
        AlertRule alertRule = new AlertRule();
        alertRule.setDeleted(CommonConstant.NO);

        // Name
        if (StringUtils.isNotEmpty(name)) {
            alertRule.setName(name);
            matching = matching.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        }

        // Condition
        Example<AlertRule> example = Example.of(alertRule, matching);
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "id"));

        // Pagination
        PageDTO<AlertRule> pageDTO = new PageDTO<>();

        // Query
        Page<AlertRule> pageList = this.alertRuleRepository.findAll(example, pageRequest);
        if (!pageList.isEmpty()) {
            pageDTO.setPage(page);
            pageDTO.setSize(size);
            pageDTO.setTotal(pageList.getTotalElements());
            pageDTO.setList(pageList.toList());
        }
        return pageDTO;
    }
}
