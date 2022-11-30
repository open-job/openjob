package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.AdminConfigDAO;
import io.openjob.server.repository.entity.AdminConfig;
import io.openjob.server.repository.repository.AdminConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */
@Component
public class AdminConfigDAOImpl implements AdminConfigDAO {
    private final AdminConfigRepository adminConfigRepository;

    @Autowired
    public AdminConfigDAOImpl(AdminConfigRepository adminConfigRepository) {
        this.adminConfigRepository = adminConfigRepository;
    }

    @Override
    public Long add(AdminConfig entity) {
        return adminConfigRepository.save(entity).getId();
    }

    @Override
    public void batchAdd(List<AdminConfig> entityList) {
        adminConfigRepository.saveAll(entityList);
    }

    @Override
    public AdminConfig getById(Long id) {
        return adminConfigRepository.getById(id);
    }

    @Override
    public Integer updateById(AdminConfig entity) {
        // return adminConfigRepository.updateById(entity); // TODO
        return 0;
    }
}

