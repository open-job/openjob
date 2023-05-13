package io.openjob.server.admin.service.impl;

import io.openjob.server.admin.constant.AdminHttpStatusEnum;
import io.openjob.server.admin.service.AdminUserService;
import io.openjob.server.repository.dao.AdminUserDAO;
import io.openjob.server.repository.entity.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author inhere
 * @since 1.0.0
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {


    private final AdminUserDAO adminUserDAO;

    @Autowired
    public AdminUserServiceImpl(AdminUserDAO adminUserDAO) {
        this.adminUserDAO = adminUserDAO;
    }


    @Override
    public AdminUser getBySessionKey(String sessionKey) {
        AdminUser adminUser = this.adminUserDAO.getBySessionKey(sessionKey);
        if (Objects.isNull(adminUser)) {
            AdminHttpStatusEnum.NOT_FOUND.throwException();
        }
        return adminUser;
    }
}