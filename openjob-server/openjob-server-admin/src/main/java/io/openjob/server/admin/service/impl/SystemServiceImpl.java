package io.openjob.server.admin.service.impl;

import io.openjob.server.admin.request.system.AdminSystemUpdateRequest;
import io.openjob.server.admin.service.SystemService;
import io.openjob.server.admin.vo.system.AdminSystemUpdateVO;
import io.openjob.server.admin.vo.system.AdminSystemVO;
import io.openjob.server.common.util.ObjectUtil;
import io.openjob.server.repository.dao.SystemDAO;
import io.openjob.server.repository.entity.System;
import org.springframework.beans.BeanUtils;
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
        System adminSystem = systemDAO.getOne();
        return ObjectUtil.mapObject(adminSystem, AdminSystemVO.class);
    }

    @Override
    public AdminSystemUpdateVO update(AdminSystemUpdateRequest request) {
        System system = new System();
        BeanUtils.copyProperties(request, system);

        AdminSystemUpdateVO retVo = new AdminSystemUpdateVO();
        systemDAO.updateById(system);
        return retVo;


    }
}
