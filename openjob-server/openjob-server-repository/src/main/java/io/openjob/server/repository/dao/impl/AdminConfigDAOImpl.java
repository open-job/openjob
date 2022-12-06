package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.AdminConfigDAO;
import io.openjob.server.repository.entity.AdminConfig;
import io.openjob.server.repository.repository.AdminConfigRepository;
import io.openjob.server.repository.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */
@Component
public class AdminConfigDAOImpl implements AdminConfigDAO {
    private final AdminConfigRepository adminConfigRepository;

    @Autowired
    public AdminConfigDAOImpl(AdminConfigRepository adminConfigRepository) {
        this.adminConfigRepository = adminConfigRepository;
    }

    @Override
    public Long add(AdminConfig entity) {
        return adminConfigRepository.save(entity).getId();
    }

    @Override
    public void batchAdd(List<AdminConfig> entityList) {
        adminConfigRepository.saveAll(entityList);
    }

    @Override
    public AdminConfig getById(Long id) {
        return adminConfigRepository.findById(id).orElse(null);
    }

    @Override
    public Integer updateById(AdminConfig entity) {
        adminConfigRepository.save(entity);
        return 1;
    }

    @Override
    public Page<AdminConfig> getPageList(Integer page, Integer size) {
        // TIP: page start from 0 on JPA.
        PageRequest pageReq = PageRequest.of(page - 1, size, EntityUtil.DEFAULT_SORT);

        return adminConfigRepository.findAll(pageReq);
    }
}

