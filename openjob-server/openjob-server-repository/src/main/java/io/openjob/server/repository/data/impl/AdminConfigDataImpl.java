package io.openjob.server.repository.data.impl;

import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.repository.dao.AdminConfigDAO;
import io.openjob.server.repository.data.AdminConfigData;
import io.openjob.server.repository.dto.AdminConfigDTO;
import io.openjob.server.repository.entity.AdminConfig;
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
        BeanUtils.copyProperties(EntityUtil.initDefaults(dto), entity);

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

        return BeanMapperUtil.map(entity, AdminConfigDTO.class);
    }

    @Override
    public Integer updateById(AdminConfigDTO dto) {
        AdminConfig entity = new AdminConfig();
        BeanMapperUtil.mapObject(dto, entity);

        return adminConfigDAO.updateById(entity);
    }

    @Override
    public List<AdminConfigDTO> getPageList(Long id) {
        // TODO
        return null;
    }
}

