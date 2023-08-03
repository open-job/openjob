package io.openjob.server.repository.dao.impl;

import io.openjob.common.constant.CommonConstant;
import io.openjob.common.util.DateUtil;
import io.openjob.server.repository.dao.AdminUserDAO;
import io.openjob.server.repository.entity.AdminUser;
import io.openjob.server.repository.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        this.adminUserRepository.saveAll(entityList);
    }

    @Override
    public void updateLogin(Long id, String ip, Long time) {
        this.adminUserRepository.updateLogin(id, ip, time);
    }

    @Override
    public void updatePassword(Long id, String nickname, String password, String token) {
        this.adminUserRepository.updatePassword(id, nickname, password, token, DateUtil.timestamp());
    }

    @Override
    public AdminUser getById(Long id) {
        return this.adminUserRepository.findById(id).orElse(null);
    }

    @Override
    public AdminUser getByUsername(String username) {
        return this.adminUserRepository.findByUsername(username);
    }

    @Override
    public AdminUser getByToken(String token) {
        return this.adminUserRepository.findByTokenAndDeleted(token, CommonConstant.NO);
    }

    @Override
    public AdminUser getBySessionKey(String sessionKey) {
        return this.adminUserRepository.findBySessionKeyAndDeleted(sessionKey, CommonConstant.NO);
    }

    @Override
    public Page<AdminUser> getPageList(Integer page, Integer size) {
        // TIP: page start from 0 on JPA.
        PageRequest pageReq = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createTime"));

        return this.adminUserRepository.findAll(pageReq);
    }
}

