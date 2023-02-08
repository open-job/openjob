package io.openjob.server.repository.data.impl;

import io.openjob.common.util.DateUtil;
import io.openjob.server.repository.dao.AdminSessionDAO;
import io.openjob.server.repository.data.AdminSessionData;
import io.openjob.server.repository.dto.AdminSessionDTO;
import io.openjob.server.repository.entity.AdminSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author inhere
 * @date 2023-02-08 14:21:20
 * @since 1.0.0
 */
@Component
public class AdminSessionDataImpl implements AdminSessionData {

    private final AdminSessionDAO adminSessionDAO;
    // private final RedisOperation redisOperation;

    @Autowired
    public AdminSessionDataImpl(AdminSessionDAO adminSessionDAO) {
        this.adminSessionDAO = adminSessionDAO;
    }

    @Override
    public Long add(AdminSessionDTO dto) {
        AdminSession entity = new AdminSession();
        BeanUtils.copyProperties(dto, entity);

        long curTime = DateUtil.timestamp();
        entity.setCreateTime(curTime);
        entity.setUpdateTime(curTime);

        return adminSessionDAO.add(entity);
    }

    @Override
    public void batchAdd(List<AdminSessionDTO> dtoList) {
        List<AdminSession> entityList = new ArrayList<>();
        // TODO copy data

        adminSessionDAO.batchAdd(entityList);
    }

    @Override
    public AdminSessionDTO getById(Long id) {
        AdminSession entity = adminSessionDAO.getById(id);

        return entityToDto(entity);
    }

    private AdminSessionDTO entityToDto(AdminSession entity) {
        AdminSessionDTO entDTO = null;

        if (Objects.nonNull(entity)) {
            entDTO = new AdminSessionDTO();
            BeanUtils.copyProperties(entity, entDTO);
        }
        return entDTO;
    }

    @Override
    public Integer updateById(AdminSessionDTO dto) {
        AdminSession entity = new AdminSession();
        BeanUtils.copyProperties(dto, entity);

        return adminSessionDAO.updateById(entity);
    }

    @Override
    public Page<AdminSessionDTO> getPageList(Integer page, Integer size) {
        Page<AdminSession> entPage = adminSessionDAO.getPageList(page, size);
        List<AdminSessionDTO> dtoList = new ArrayList<>();

        entPage.forEach(entity -> {
            AdminSessionDTO entDTO = new AdminSessionDTO();
            BeanUtils.copyProperties(entity, entDTO);
            dtoList.add(entDTO);
        });

        return new PageImpl<>(dtoList, entPage.getPageable(), entPage.getTotalElements());
    }

}

