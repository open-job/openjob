package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.AdminRuleDAO;
import io.openjob.server.repository.entity.AdminRule;
import io.openjob.server.repository.repository.AdminRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */
@Component
public class AdminRuleDAOImpl implements AdminRuleDAO {
    private final AdminRuleRepository adminRuleRepository;

    @Autowired
    public AdminRuleDAOImpl(AdminRuleRepository adminRuleRepository) {
        this.adminRuleRepository = adminRuleRepository;
    }

    @Override
    public Long add(AdminRule entity) {
        return adminRuleRepository.save(entity).getId();
    }

    @Override
    public void batchAdd(List<AdminRule> entityList) {
        adminRuleRepository.saveAll(entityList);
    }

    @Override
    public AdminRule getById(Long id) {
        return adminRuleRepository.getById(id);
    }

    @Override
    public Integer updateById(AdminRule entity) {
        // return adminRuleRepository.updateById(entity); // TODO
        return 0;
    }

    @Override
    public List<AdminRule> getByIds(List<Long> ids) {
        return adminRuleRepository.findByIdIn(ids);
    }
}

