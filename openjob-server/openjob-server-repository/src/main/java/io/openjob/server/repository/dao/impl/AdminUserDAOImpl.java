package io.openjob.server.repository.dao.impl;

import io.openjob.common.constant.CommonConstant;
import io.openjob.server.repository.dao.AdminUserDAO;
import io.openjob.server.repository.entity.AdminUser;
import io.openjob.server.repository.repository.AdminUserRepository;
import io.openjob.server.repository.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        return adminUserRepository.findById(id).orElse(null);
    }

    @Override
    public Integer updateById(AdminUser entity) {
        adminUserRepository.save(entity);
        return 1;
    }

    @Override
    public AdminUser getByUsername(String username) {
        return adminUserRepository.findByUsername(username);
    }

    @Override
    public AdminUser getByToken(String token) {
        return adminUserRepository.findByTokenAndDeleted(token, CommonConstant.NO);
    }

    @Override
    public Page<AdminUser> getPageList(Integer page, Integer size) {
        // TIP: page start from 0 on JPA.
        PageRequest pageReq = PageRequest.of(page - 1, size, EntityUtil.DEFAULT_SORT);

        return adminUserRepository.findAll(pageReq);
    }
}

