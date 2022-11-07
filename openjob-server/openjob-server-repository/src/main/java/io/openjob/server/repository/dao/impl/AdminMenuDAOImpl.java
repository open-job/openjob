package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.AdminMenuDAO;
import io.openjob.server.repository.entity.AdminMenu;
import io.openjob.server.repository.repository.AdminMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 21:34:58
 * @since 1.0.0
 */
@Component
public class AdminMenuDAOImpl implements AdminMenuDAO {
    private final AdminMenuRepository adminMenuRepository;

    @Autowired
    public AdminMenuDAOImpl(AdminMenuRepository adminMenuRepository) {
        this.adminMenuRepository = adminMenuRepository;
    }

    @Override
    public Long add(AdminMenu entity) {
        return adminMenuRepository.save(entity).getId();
    }

    @Override
    public void batchAdd(List<AdminMenu> entityList) {
        adminMenuRepository.saveAll(entityList);
    }

    @Override
    public AdminMenu getById(Long id) {
        return adminMenuRepository.getById(id);
    }

    @Override
    public Integer updateById(AdminMenu entity) {
        return adminMenuRepository.updateById(entity);
    }
}

