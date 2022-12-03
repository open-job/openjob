package io.openjob.server.repository.data.impl;

import io.openjob.common.util.DateUtil;
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
        entity.setCreateTime(DateUtil.timestamp());

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

    @Override
    public List<AdminMenuDTO> getByIds(List<Long> ids) {
        List<AdminMenu> entList = adminMenuDAO.getByIds(ids);
        List<AdminMenuDTO> dtoList = new ArrayList<>();

        entList.forEach(entity -> {
            AdminMenuDTO entDTO = new AdminMenuDTO();
            BeanUtils.copyProperties(entity, entDTO);
            dtoList.add(entDTO);
        });

        return dtoList;
    }
}

