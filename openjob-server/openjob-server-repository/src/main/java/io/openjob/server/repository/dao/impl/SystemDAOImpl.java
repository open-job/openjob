package io.openjob.server.repository.dao.impl;

import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.repository.dao.SystemDAO;
import io.openjob.server.repository.entity.System;
import io.openjob.server.repository.repository.SystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class SystemDAOImpl implements SystemDAO {

    /**
     * Default id.
     */
    private static final Integer DEFAULT_ID = 1;

    private final SystemRepository systemRepository;

    @Autowired
    public SystemDAOImpl(SystemRepository systemRepository) {
        this.systemRepository = systemRepository;
    }

    @Override
    public System getOne() {
        return this.systemRepository.findById(DEFAULT_ID).orElseGet(System::new);
    }

    @Override
    public Integer updateClusterVersion(Long clusterVersion) {
        return systemRepository.updateClusterVersion(DEFAULT_ID, clusterVersion);
    }

    @Override
    public void updateById(System system) {
        System old = this.getOne();

        // Max slot
        if (Objects.nonNull(system.getMaxSlot())){
            old.setMaxSlot(system.getMaxSlot());
        }

        // Delay zset slot
        if (Objects.nonNull(system.getDelayZsetSlot())){
            old.setDelayZsetSlot(system.getDelayZsetSlot());
        }

        // Delay add list slot
        if (Objects.nonNull(system.getDelayAddListSlot())){
            old.setDelayAddListSlot(system.getDelayAddListSlot());
        }

        // Delay status list slot
        if (Objects.nonNull(system.getDelayStatusListSlot())){
            old.setDelayStatusListSlot(system.getDelayStatusListSlot());
        }

        // Delay delete list slot
        if (Objects.nonNull(system.getDelayDeleteListSlot())){
            old.setDelayDeleteListSlot(system.getDelayDeleteListSlot());
        }
        systemRepository.save(old);
    }
}
