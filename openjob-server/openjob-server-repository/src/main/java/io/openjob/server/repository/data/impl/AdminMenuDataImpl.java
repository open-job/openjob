package io.openjob.server.repository.data.impl;

import io.openjob.server.common.util.ObjectUtil;
import io.openjob.server.repository.dao.AdminMenuDAO;
import io.openjob.server.repository.data.AdminMenuData;
import io.openjob.server.repository.dto.AdminMenuDTO;
import io.openjob.server.repository.entity.AdminMenu;
import io.openjob.server.repository.mapper.AdminMenuMapper;
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
public class AdminMenuDataImpl implements AdminMenuData {

    private final AdminMenuDAO adminMenuDAO;

    private final AdminMenuMapper adminMenuMapper;

    @Autowired
    public AdminMenuDataImpl(AdminMenuDAO adminMenuDAO, AdminMenuMapper adminMenuMapper) {
        this.adminMenuDAO = adminMenuDAO;
        this.adminMenuMapper = adminMenuMapper;
    }

    @Override
    public Long add(AdminMenuDTO dto) {
        AdminMenu entity = new AdminMenu();
        BeanUtils.copyProperties(EntityUtil.initDefaults(dto), entity);

        return adminMenuDAO.add(entity);
    }

    @Override
    public void batchAdd(List<AdminMenuDTO> dtoList) {
        List<AdminMenu> entityList = new ArrayList<>();
        // TODO copy data

        adminMenuDAO.batchAdd(entityList);
    }

    @Override
    public AdminMenuDTO getById(Long id) {
        AdminMenu entity = adminMenuDAO.getById(id);

        return ObjectUtil.mapObject(entity, AdminMenuDTO.class);
    }

    @Override
    public Integer updateById(AdminMenuDTO dto) {
        AdminMenu entity = adminMenuMapper.partialUpdate(dto, new AdminMenu());

        return adminMenuDAO.updateById(entity);
    }

    @Override
    public List<AdminMenuDTO> getPageList(Long id) {
        return null;
    }

    @Override
    public List<AdminMenuDTO> getByIds(List<Long> ids) {
        List<AdminMenu> entList = adminMenuDAO.getByIds(ids);
        List<AdminMenuDTO> dtoList = new ArrayList<>();

        entList.forEach(entity -> dtoList.add(adminMenuMapper.toDto(entity)));

        return dtoList;
    }

    @Override
    public List<AdminMenuDTO> getAllMenus() {
        List<AdminMenu> entList = adminMenuDAO.getAllMenus();
        List<AdminMenuDTO> dtoList = new ArrayList<>();

        entList.forEach(entity -> dtoList.add(adminMenuMapper.toDto(entity)));

        return dtoList;
    }
}

