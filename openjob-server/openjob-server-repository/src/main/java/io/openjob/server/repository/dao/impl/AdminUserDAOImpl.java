package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.AdminUserDAO;
import io.openjob.server.repository.entity.AdminUser;
import io.openjob.server.repository.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */
@Component
public class AdminUserDAOImpl implements AdminUserDAO {
    private final AdminUserRepository adminUserRepository;

    @Autowired
    public AdminUserDAOImpl(AdminUserRepository adminUserRepository) {
        this.adminUserRepository = adminUserRepository;
    }

    @Override
    public Long add(AdminUser entity) {
        return adminUserRepository.save(entity).getId();
    }

    @Override
    public void batchAdd(List<AdminUser> entityList) {
        adminUserRepository.saveAll(entityList);
    }

    @Override
    public AdminUser getById(Long id) {
        return adminUserRepository.getById(id);
    }

    @Override
    public Integer updateById(AdminUser entity) {
        // return adminUserRepository.updateById(entity); // TODO
        return 0;
    }
}

