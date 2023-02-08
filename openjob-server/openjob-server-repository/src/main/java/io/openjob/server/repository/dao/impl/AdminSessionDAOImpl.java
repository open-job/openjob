package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.AdminSessionDAO;
import io.openjob.server.repository.entity.AdminSession;
import io.openjob.server.repository.repository.AdminSessionRepository;
import io.openjob.server.repository.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author inhere
 * @date 2023-02-08 14:21:18
 * @since 1.0.0
 */
@Component
public class AdminSessionDAOImpl implements AdminSessionDAO {
    private final AdminSessionRepository adminSessionRepository;

    @Autowired
    public AdminSessionDAOImpl(AdminSessionRepository adminSessionRepository) {
        this.adminSessionRepository = adminSessionRepository;
    }

    @Override
    public Long add(AdminSession entity) {
        return adminSessionRepository.save(entity).getId();
    }

    @Override
    public void batchAdd(List<AdminSession> entityList) {
        adminSessionRepository.saveAll(entityList);
    }

    @Override
    public AdminSession getById(Long id) {
        return adminSessionRepository.findById(id).orElse(null);
    }

    @Override
    public Integer updateById(AdminSession entity) {
        adminSessionRepository.save(entity);
        return 1;
    }

    @Override
    public Page<AdminSession> getPageList(Integer page, Integer size) {
        // TIP: page start from 0 on JPA.
        PageRequest pageReq = PageRequest.of(page - 1, size, EntityUtil.DEFAULT_SORT);

        return adminSessionRepository.findAll(pageReq);
    }
}

