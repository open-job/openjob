package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.AdminMenuDAO;
import io.openjob.server.repository.entity.AdminMenu;
import io.openjob.server.repository.repository.AdminMenuRepository;
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
public class AdminMenuDAOImpl implements AdminMenuDAO {
    private final AdminMenuRepository adminMenuRepository;

    @Autowired
    public AdminMenuDAOImpl(AdminMenuRepository adminMenuRepository) {
        this.adminMenuRepository = adminMenuRepository;
    }

    @Override
    public Long add(AdminMenu entity) {
        return adminMenuRepository.save(entity).getId();
    }

    @Override
    public void batchAdd(List<AdminMenu> entityList) {
        adminMenuRepository.saveAll(entityList);
    }

    @Override
    public AdminMenu getById(Long id) {
        return adminMenuRepository.findById(id).orElse(null);
    }

    @Override
    public Integer updateById(AdminMenu entity) {
        adminMenuRepository.save(entity);
        return 1;
    }

    @Override
    public List<AdminMenu> getByIds(List<Long> ids) {
        return adminMenuRepository.findByIdIn(ids);
    }

    @Override
    public Page<AdminMenu> getPageList(Integer page, Integer size) {
        // TIP: page start from 0 on JPA.
        PageRequest pageReq = PageRequest.of(page - 1, size, EntityUtil.DEFAULT_SORT);

        return adminMenuRepository.findAll(pageReq);
    }
}

