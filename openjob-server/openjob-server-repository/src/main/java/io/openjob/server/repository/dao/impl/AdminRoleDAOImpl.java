package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.AdminRoleDAO;
import io.openjob.server.repository.entity.AdminRole;
import io.openjob.server.repository.repository.AdminRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */
@Component
public class AdminRoleDAOImpl implements AdminRoleDAO {
    private final AdminRoleRepository adminRoleRepository;

    @Autowired
    public AdminRoleDAOImpl(AdminRoleRepository adminRoleRepository) {
        this.adminRoleRepository = adminRoleRepository;
    }

    @Override
    public Long add(AdminRole entity) {
        return adminRoleRepository.save(entity).getId();
    }

    @Override
    public void batchAdd(List<AdminRole> entityList) {
        adminRoleRepository.saveAll(entityList);
    }

    @Override
    public AdminRole getById(Long id) {
        return adminRoleRepository.findById(id).orElse(null);
    }

    @Override
    public Integer updateById(AdminRole entity) {
        // return adminRoleRepository.updateById(entity); // TODO
        return 0;
    }

    @Override
    public List<AdminRole> getByIds(List<Long> ids) {
        return adminRoleRepository.findByIdIn(ids);
    }

    @Override
    public Page<AdminRole> getPageList(Integer page, Integer size) {
        PageRequest pageReq = PageRequest.of(page, size, Sort.by("create_time"));

        return adminRoleRepository.findAll(pageReq);
    }
}

