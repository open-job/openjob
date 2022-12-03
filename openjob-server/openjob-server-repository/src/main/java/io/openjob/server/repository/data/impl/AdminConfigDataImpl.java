package io.openjob.server.repository.data.impl;

import io.openjob.common.util.DateUtil;
import io.openjob.server.common.util.ObjectUtil;
import io.openjob.server.repository.dao.AdminConfigDAO;
import io.openjob.server.repository.data.AdminConfigData;
import io.openjob.server.repository.dto.AdminConfigDTO;
import io.openjob.server.repository.entity.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * @author inhere
 * @since 1.0.0
 */
@Component
public class AdminConfigDataImpl implements AdminConfigData {

    private final AdminConfigDAO adminConfigDAO;
    // private final RedisOperation redisOperation;

    @Autowired
    public AdminConfigDataImpl(AdminConfigDAO adminConfigDAO) {
        this.adminConfigDAO = adminConfigDAO;
    }

    @Override
    public Long add(AdminConfigDTO dto) {
        AdminConfig entity = new AdminConfig();
        ObjectUtil.copyObject(dto, entity);
        entity.setCreateTime(DateUtil.timestamp());

        return adminConfigDAO.add(entity);
    }

    @Override
    public void batchAdd(List<AdminConfigDTO> dtoList) {
        List<AdminConfig> entityList = new ArrayList<>();
        // TODO copy data

        adminConfigDAO.batchAdd(entityList);
    }

    @Override
    public AdminConfigDTO getById(Long id) {
        AdminConfig entity = adminConfigDAO.getById(id);

        return ObjectUtil.mapObject(entity, AdminConfigDTO.class);
    }

    @Override
    public Integer updateById(AdminConfigDTO dto) {
        AdminConfig entity = new AdminConfig();
        ObjectUtil.copyObject(dto, entity);

        return adminConfigDAO.updateById(entity);
    }

    @Override
    public List<AdminConfigDTO> getPageList(Long id) {
        // TODO
        return null;
    }
}

