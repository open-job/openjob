package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.SystemDAO;
import io.openjob.server.repository.entity.System;
import io.openjob.server.repository.repository.SystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public Integer updateClusterVersion() {
        return systemRepository.updateClusterVersion(DEFAULT_ID);
    }
}
