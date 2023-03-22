package io.openjob.server.admin.service.impl;

import io.openjob.server.admin.request.system.SystemUpdateRequest;
import io.openjob.server.admin.service.SystemService;
import io.openjob.server.admin.vo.system.SystemUpdateVO;
import io.openjob.server.admin.vo.system.SystemVO;
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
    public SystemVO getLatest() {
        System adminSystem = this.systemDAO.getOne();
        return BeanMapperUtil.map(adminSystem, SystemVO.class);
    }

    @Override
    public SystemUpdateVO update(SystemUpdateRequest request) {
        this.systemDAO.updateById(BeanMapperUtil.map(request, System.class));
        return new SystemUpdateVO();
    }
}
