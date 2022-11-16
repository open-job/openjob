package io.openjob.server.repository.data.impl;

import io.openjob.server.repository.dao.AdminMenuDAO;
import io.openjob.server.repository.data.AdminMenuData;
import io.openjob.server.repository.dto.AdminMenuDTO;
import io.openjob.server.repository.entity.AdminMenu;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * @author inhere
 * @date 2022-11-13 23:10:30
 * @since 1.0.0
 */
@Component
public class AdminMenuDataImpl implements AdminMenuData {

    private final AdminMenuDAO adminMenuDAO;
    // private final RedisOperation redisOperation;

    @Autowired
    public AdminMenuDataImpl(AdminMenuDAO adminMenuDAO) {
        this.adminMenuDAO = adminMenuDAO;
    }

    @Override
    public Long add(AdminMenuDTO dto) {
        AdminMenu entity = new AdminMenu();
        BeanUtils.copyProperties(dto, entity);

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
        AdminMenuDTO entDTO = new AdminMenuDTO();
        BeanUtils.copyProperties(entity, entDTO);

        return entDTO;
    }

    @Override
    public Integer updateById(AdminMenuDTO dto) {
        AdminMenu entity = new AdminMenu();
        BeanUtils.copyProperties(dto, entity);

        return adminMenuDAO.updateById(entity);
    }

    @Override
    public List<AdminMenuDTO> getPageList(Long id) {
        return null;
    }
}

