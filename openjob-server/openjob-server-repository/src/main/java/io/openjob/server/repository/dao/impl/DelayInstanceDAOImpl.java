package io.openjob.server.repository.dao.impl;

import io.openjob.common.util.DateUtil;
import io.openjob.server.repository.dao.DelayInstanceDAO;
import io.openjob.server.repository.entity.DelayInstance;
import io.openjob.server.repository.repository.DelayInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class DelayInstanceDAOImpl implements DelayInstanceDAO {

    private final DelayInstanceRepository delayInstanceRepository;

    @Autowired
    public DelayInstanceDAOImpl(DelayInstanceRepository delayInstanceRepository) {
        this.delayInstanceRepository = delayInstanceRepository;
    }

    @Override
    public Long save(DelayInstance delayInstance) {
        return this.delayInstanceRepository.save(delayInstance).getId();
    }

    @Override
    public List<DelayInstance> listDelayInstance(List<Long> slotIds, Integer time, Integer size) {
        DelayInstance delayInstance = new DelayInstance();
        return this.delayInstanceRepository.findAll(Example.of(delayInstance), PageRequest.of(0, size, Sort.by("id"))).toList();
    }

    @Override
    public Integer batchUpdateStatus(List<Long> ids, Integer status) {
        return this.delayInstanceRepository.batchUpdateStatus(ids, status, DateUtil.now());
    }
}
