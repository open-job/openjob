package io.openjob.server.repository.data.impl;

import io.openjob.common.util.DateUtil;
import io.openjob.server.common.util.ObjectUtil;
import io.openjob.server.repository.dao.AdminUserDAO;
import io.openjob.server.repository.data.AdminUserData;
import io.openjob.server.repository.dto.AdminUserDTO;
import io.openjob.server.repository.entity.AdminUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
        entity.setCreateTime(DateUtil.timestamp());

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
        return ObjectUtil.mapObject(adminUserDAO.getById(id), AdminUserDTO.class);
    }

    @Override
    public AdminUserDTO getByUsername(String username) {
        AdminUser entity = adminUserDAO.getByUsername(username);

        return ObjectUtil.mapObject(entity, AdminUserDTO.class);
    }

    @Override
    public Integer updateById(AdminUserDTO dto) {
        AdminUser entity = new AdminUser();
        BeanUtils.copyProperties(dto, entity);

        return adminUserDAO.updateById(entity);
    }

    @Override
    public Page<AdminUserDTO> getPageList(Integer page, Integer size) {
        Page<AdminUser> entPage = adminUserDAO.getPageList(page, size);
        List<AdminUserDTO> dtoList = new ArrayList<>();

        entPage.forEach(entity -> {
            dtoList.add(ObjectUtil.copyObject(entity, new AdminUserDTO()));
        });

        return new PageImpl<>(dtoList, entPage.getPageable(), entPage.getTotalElements());
    }
}

