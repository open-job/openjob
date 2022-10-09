package io.openjob.server.repository.dao.impl;

import io.openjob.common.constant.InstanceStatusEnum;
import io.openjob.server.repository.dao.DelayInstanceDAO;
import io.openjob.server.repository.entity.DelayInstance;
import io.openjob.server.repository.repository.DelayInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<DelayInstance> listDelayInstance(List<Long> slotIds, Integer time) {
        return this.delayInstanceRepository.findBySlotsIdInAndStatusAndAndExecuteTimeLessThanEqual(slotIds, InstanceStatusEnum.WAITING.getStatus(), time);
    }
}
