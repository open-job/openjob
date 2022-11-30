package io.openjob.server.repository.data.impl;

import io.openjob.server.repository.dao.AdminRuleDAO;
import io.openjob.server.repository.data.AdminRuleData;
import io.openjob.server.repository.dto.AdminRuleDTO;
import io.openjob.server.repository.entity.AdminRule;
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
    public Integer updateById(AdminRuleDTO dto) {
        AdminRule entity = new AdminRule();
        BeanUtils.copyProperties(dto, entity);

        return adminRuleDAO.updateById(entity);
    }

    @Override
    public List<AdminRuleDTO> getPageList(Long id) {
        // TODO
        return null;
    }
}

