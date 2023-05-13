package io.openjob.server.repository.dao.impl;

import io.openjob.common.constant.CommonConstant;
import io.openjob.server.repository.constant.PermissionTypeEnum;
import io.openjob.server.repository.dao.AdminPermissionDAO;
import io.openjob.server.repository.entity.AdminPermission;
import io.openjob.server.repository.repository.AdminPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
public class AdminPermissionDAOImpl implements AdminPermissionDAO {
    private final AdminPermissionRepository adminPermRepository;

    @Autowired
    public AdminPermissionDAOImpl(AdminPermissionRepository adminPermRepository) {
        this.adminPermRepository = adminPermRepository;
    }

    @Override
    public Long add(AdminPermission entity) {
        return adminPermRepository.save(entity).getId();
    }

    @Override
    public void batchAdd(List<AdminPermission> entityList) {
        adminPermRepository.saveAll(entityList);
    }

    @Override
    public AdminPermission getById(Long id) {
        return adminPermRepository.findById(id).orElse(null);
    }

    @Override
    public Integer updateById(AdminPermission entity) {
        adminPermRepository.save(entity);
        return 1;
    }

    @Override
    public List<AdminPermission> getByIds(List<Long> ids) {
        return adminPermRepository.findByIdIn(ids);
    }

    @Override
    public Page<AdminPermission> getPageList(Integer page, Integer size) {
        // TIP: page start from 0 on JPA.
        PageRequest pageReq = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createTime"));

        return adminPermRepository.findAll(pageReq);
    }

    @Override
    public List<AdminPermission> getPermissionList(PermissionTypeEnum permissionType) {
        AdminPermission cond = new AdminPermission();
        cond.setDeleted(CommonConstant.NO);
        cond.setType(permissionType.getType());

        Example<AdminPermission> ex = Example.of(cond);

        return adminPermRepository.findAll(ex, Sort.by(Sort.Direction.ASC, "sort"));
    }
}

