package io.openjob.server.repository.data.impl;

import io.openjob.common.util.DateUtil;
import io.openjob.server.repository.dao.AdminRuleDAO;
import io.openjob.server.repository.data.AdminRuleData;
import io.openjob.server.repository.dto.AdminRuleDTO;
import io.openjob.server.repository.entity.AdminRule;
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
 * @since 1.0.0
 */
@Component
public class AdminRuleDataImpl implements AdminRuleData {

    private final AdminRuleDAO adminRuleDAO;
    // private final RedisOperation redisOperation;

    @Autowired
    public AdminRuleDataImpl(AdminRuleDAO adminRuleDAO) {
        this.adminRuleDAO = adminRuleDAO;
    }

    @Override
    public Long add(AdminRuleDTO dto) {
        AdminRule entity = new AdminRule();
        BeanUtils.copyProperties(dto, entity);
        entity.setCreateTime(DateUtil.timestamp());

        return adminRuleDAO.add(entity);
    }

    @Override
    public void batchAdd(List<AdminRuleDTO> dtoList) {
        List<AdminRule> entityList = new ArrayList<>();
        // TODO copy data

        adminRuleDAO.batchAdd(entityList);
    }

    @Override
    public AdminRuleDTO getById(Long id) {
        AdminRule entity = adminRuleDAO.getById(id);
        AdminRuleDTO entDTO = new AdminRuleDTO();
        BeanUtils.copyProperties(entity, entDTO);

        return entDTO;
    }

    @Override
    public List<AdminRuleDTO> getByIds(List<Long> ids) {
        List<AdminRule> entList = adminRuleDAO.getByIds(ids);
        List<AdminRuleDTO> dtoList = new ArrayList<>();

        entList.forEach(entity -> {
            AdminRuleDTO entDTO = new AdminRuleDTO();
            BeanUtils.copyProperties(entity, entDTO);
            dtoList.add(entDTO);
        });

        return dtoList;
    }

    @Override
    public Integer updateById(AdminRuleDTO dto) {
        AdminRule entity = new AdminRule();
        BeanUtils.copyProperties(dto, entity);

        return adminRuleDAO.updateById(entity);
    }

    @Override
    public Page<AdminRuleDTO> getPageList(Integer page, Integer size) {
        Page<AdminRule> entPage = adminRuleDAO.getPageList(page, size);
        List<AdminRuleDTO> dtoList = new ArrayList<>();

        entPage.forEach(entity -> {
            AdminRuleDTO entDTO = new AdminRuleDTO();
            BeanUtils.copyProperties(entity, entDTO);
            dtoList.add(entDTO);
        });

        return new PageImpl<>(dtoList, entPage.getPageable(), entPage.getTotalElements());
    }

    private AdminRuleDTO entityToDto(AdminRule entity) {
        AdminRuleDTO entDTO = null;

        if (Objects.nonNull(entity)) {
            entDTO = new AdminRuleDTO();
            BeanUtils.copyProperties(entity, entDTO);
        }
        return entDTO;
    }

}

