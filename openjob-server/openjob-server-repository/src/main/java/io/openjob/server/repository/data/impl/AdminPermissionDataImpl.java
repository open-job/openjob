package io.openjob.server.repository.data.impl;

import io.openjob.server.common.util.ObjectUtil;
import io.openjob.server.repository.dao.AdminPermissionDAO;
import io.openjob.server.repository.data.AdminPermissionData;
import io.openjob.server.repository.dto.AdminPermissionDTO;
import io.openjob.server.repository.entity.AdminPermission;
import io.openjob.server.repository.mapper.AdminPermissionMapper;
import io.openjob.server.repository.util.EntityUtil;
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
public class AdminPermissionDataImpl implements AdminPermissionData {

    private final AdminPermissionDAO adminPermDAO;

    private final AdminPermissionMapper adminPermMapper;

    @Autowired
    public AdminPermissionDataImpl(AdminPermissionDAO adminPermDAO, AdminPermissionMapper adminPermMapper) {
        this.adminPermDAO = adminPermDAO;
        this.adminPermMapper = adminPermMapper;
    }

    @Override
    public Long add(AdminPermissionDTO dto) {
        AdminPermission entity = new AdminPermission();
        BeanUtils.copyProperties(EntityUtil.initDefaults(dto), entity);

        return adminPermDAO.add(entity);
    }

    @Override
    public void batchAdd(List<AdminPermissionDTO> dtoList) {
        List<AdminPermission> entityList = new ArrayList<>();
        // TODO copy data

        adminPermDAO.batchAdd(entityList);
    }

    @Override
    public AdminPermissionDTO getById(Long id) {
        AdminPermission entity = adminPermDAO.getById(id);

        return ObjectUtil.mapObject(entity, AdminPermissionDTO.class);
    }

    @Override
    public Integer updateById(AdminPermissionDTO dto) {
        AdminPermission entity = adminPermMapper.partialUpdate(dto, new AdminPermission());

        return adminPermDAO.updateById(entity);
    }

    @Override
    public List<AdminPermissionDTO> getPageList(Long id) {
        return null;
    }
}

