package io.openjob.server.admin.service.impl;

import io.openjob.server.admin.request.system.AdminSystemUpdateRequest;
import io.openjob.server.admin.service.SystemService;
import io.openjob.server.admin.vo.system.AdminSystemUpdateVO;
import io.openjob.server.admin.vo.system.AdminSystemVO;
import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.repository.dao.SystemDAO;
import io.openjob.server.repository.entity.System;
import org.springframework.stereotype.Service;

/**
 * @author riki
 * @since 1.0.0
 */
@Service
public class SystemServiceImpl implements SystemService {

    private final SystemDAO systemDAO;

    public SystemServiceImpl(SystemDAO systemDAO) {
        this.systemDAO = systemDAO;
    }

    @Override
    public AdminSystemVO getLatest() {
        System adminSystem = this.systemDAO.getOne();
        return BeanMapperUtil.map(adminSystem, AdminSystemVO.class);
    }

    @Override
    public AdminSystemUpdateVO update(AdminSystemUpdateRequest request) {
        this.systemDAO.updateById(BeanMapperUtil.map(request, System.class));
        return new AdminSystemUpdateVO();
    }
}
