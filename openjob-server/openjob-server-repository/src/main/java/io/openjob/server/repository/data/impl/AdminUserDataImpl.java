package io.openjob.server.repository.data.impl;

import io.openjob.server.repository.dao.AdminUserDAO;
import io.openjob.server.repository.data.AdminUserData;
import io.openjob.server.repository.dto.AdminUserDTO;
import io.openjob.server.repository.entity.AdminUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * @author inhere
 * @since 1.0.0
 */
@Component
public class AdminUserDataImpl implements AdminUserData {

    private final AdminUserDAO adminUserDAO;

    @Autowired
    public AdminUserDataImpl(AdminUserDAO adminUserDAO) {
        this.adminUserDAO = adminUserDAO;
    }

    @Override
    public Long add(AdminUserDTO dto) {
        AdminUser entity = new AdminUser();
        BeanUtils.copyProperties(dto, entity);

        return adminUserDAO.add(entity);
    }

    @Override
    public void batchAdd(List<AdminUserDTO> dtoList) {
        List<AdminUser> entityList = new ArrayList<>();
        // TODO copy data

        adminUserDAO.batchAdd(entityList);
    }

    @Override
    public AdminUserDTO getById(Long id) {
        AdminUserDTO entDTO = new AdminUserDTO();
        AdminUser entity = adminUserDAO.getById(id);
        BeanUtils.copyProperties(entity, entDTO);

        return entDTO;
    }

    @Override
    public AdminUserDTO getByUsername(String username) {
        AdminUser entity = adminUserDAO.getByUsername(username);
        AdminUserDTO entDTO = new AdminUserDTO();
        BeanUtils.copyProperties(entity, entDTO);

        return entDTO;
    }

    @Override
    public Integer updateById(AdminUserDTO dto) {
        AdminUser entity = new AdminUser();
        BeanUtils.copyProperties(dto, entity);

        return adminUserDAO.updateById(entity);
    }
}

