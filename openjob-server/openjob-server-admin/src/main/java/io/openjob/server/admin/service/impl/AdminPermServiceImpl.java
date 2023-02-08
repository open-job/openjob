package io.openjob.server.admin.service.impl;

import io.openjob.common.constant.CommonConstant;
import io.openjob.server.admin.request.perm.AdminPermAddRequest;
import io.openjob.server.admin.request.perm.AdminPermDeleteRequest;
import io.openjob.server.admin.request.perm.AdminPermListRequest;
import io.openjob.server.admin.request.perm.AdminPermQueryRequest;
import io.openjob.server.admin.request.perm.AdminPermUpdateRequest;
import io.openjob.server.admin.service.AdminPermService;
import io.openjob.server.admin.vo.perm.AdminPermAddVO;
import io.openjob.server.admin.vo.perm.AdminPermQueryVO;
import io.openjob.server.admin.vo.perm.AdminPermUpdateVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.ObjectUtil;
import io.openjob.server.repository.data.AdminPermissionData;
import io.openjob.server.repository.dto.AdminPermissionDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author inhere
 * @since 1.0.0
 */
@Service
public class AdminPermServiceImpl implements AdminPermService {

    private final AdminPermissionData adminPermData;

    @Autowired
    public AdminPermServiceImpl(AdminPermissionData adminPermData) {
        this.adminPermData = adminPermData;
    }

    @Override
    public AdminPermAddVO add(AdminPermAddRequest reqDTO) {
        AdminPermissionDTO entDTO = new AdminPermissionDTO();
        BeanUtils.copyProperties(reqDTO, entDTO);

        AdminPermAddVO retVo = new AdminPermAddVO();
        retVo.setId(adminPermData.add(entDTO));

        return retVo;
    }

    @Override
    public AdminPermUpdateVO update(AdminPermUpdateRequest reqDTO) {
        AdminPermissionDTO entDTO = new AdminPermissionDTO();
        BeanUtils.copyProperties(reqDTO, entDTO);

        AdminPermUpdateVO retVo = new AdminPermUpdateVO();
        adminPermData.updateById(entDTO);
        return retVo;
    }

    @Override
    public AdminPermUpdateVO delete(AdminPermDeleteRequest reqDTO) {
        AdminPermissionDTO entDTO = new AdminPermissionDTO();
        entDTO.setId(reqDTO.getId());
        entDTO.setDeleted(CommonConstant.YES);

        AdminPermUpdateVO retVo = new AdminPermUpdateVO();
        adminPermData.updateById(entDTO);

        return retVo;
    }

    @Override
    public AdminPermQueryVO query(AdminPermQueryRequest reqDTO) {
        AdminPermissionDTO entDTO = adminPermData.getById(reqDTO.getId());

        return ObjectUtil.mapObject(entDTO, AdminPermQueryVO.class);
    }

    @Override
    public PageDTO<AdminPermQueryVO> getPageList(AdminPermListRequest reqDTO) {
        // TODO
        return null;
    }
}

