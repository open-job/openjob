package io.openjob.server.repository.data.impl;

import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.repository.dao.AdminRoleDAO;
import io.openjob.server.repository.data.AdminRoleData;
import io.openjob.server.repository.dto.AdminRoleDTO;
import io.openjob.server.repository.entity.AdminRole;
import io.openjob.server.repository.util.EntityUtil;
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
public class AdminRoleDataImpl implements AdminRoleData {

    private final AdminRoleDAO adminRoleDAO;
    // private final RedisOperation redisOperation;

    @Autowired
    public AdminRoleDataImpl(AdminRoleDAO adminRoleDAO) {
        this.adminRoleDAO = adminRoleDAO;
    }

    @Override
    public Long add(AdminRoleDTO dto) {
        AdminRole entity = new AdminRole();
        BeanUtils.copyProperties(EntityUtil.initDefaults(dto), entity);

        return adminRoleDAO.add(entity);
    }

    @Override
    public void batchAdd(List<AdminRoleDTO> dtoList) {
        List<AdminRole> entityList = new ArrayList<>();
        // TODO copy data

        adminRoleDAO.batchAdd(entityList);
    }

    @Override
    public AdminRoleDTO getById(Long id) {
        AdminRole entity = adminRoleDAO.getById(id);

        return BeanMapperUtil.map(entity, AdminRoleDTO.class);
    }

    @Override
    public List<AdminRoleDTO> getByIds(List<Long> ids) {
        List<AdminRole> entList = adminRoleDAO.getByIds(ids);
        List<AdminRoleDTO> dtoList = new ArrayList<>();

        entList.forEach(entity -> {
            dtoList.add(BeanMapperUtil.mapObject(entity, new AdminRoleDTO()));
        });

        return dtoList;
    }

    @Override
    public Integer updateById(AdminRoleDTO dto) {
        AdminRole entity = new AdminRole();
        BeanUtils.copyProperties(dto, entity);

        return adminRoleDAO.updateById(entity);
    }

    @Override
    public Page<AdminRoleDTO> getPageList(Integer page, Integer size) {
        Page<AdminRole> entPage = adminRoleDAO.getPageList(page, size);
        List<AdminRoleDTO> dtoList = new ArrayList<>();

        entPage.forEach(entity -> {
            dtoList.add(BeanMapperUtil.mapObject(entity, new AdminRoleDTO()));
        });

        return new PageImpl<>(dtoList, entPage.getPageable(), entPage.getTotalElements());
    }

}

