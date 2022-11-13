package io.openjob.server.admin.service.impl;

import io.openjob.server.common.constant.CommonConst;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.data.AdminUserData;
import io.openjob.server.admin.service.AdminUserService;
import io.openjob.server.admin.request.user.AdminUserAddRequest;
import io.openjob.server.admin.vo.user.AdminUserAddVO;
import io.openjob.server.admin.request.user.AdminUserQueryRequest;
import io.openjob.server.admin.vo.user.AdminUserQueryVO;
import io.openjob.server.admin.request.user.AdminUserUpdateRequest;
import io.openjob.server.admin.vo.user.AdminUserUpdateVO;
import io.openjob.server.admin.request.user.AdminUserDeleteRequest;
import io.openjob.server.admin.request.user.AdminUserListRequest;
import io.openjob.server.repository.dto.AdminUserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author inhere
 * @date 2022-11-09 13:30:07
 * @since 1.0.0
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    private final AdminUserData adminUserData;

    @Autowired
    public AdminUserServiceImpl(AdminUserData adminUserData) {
        this.adminUserData = adminUserData;
    }

    @Override
    public AdminUserAddVO add(AdminUserAddRequest reqDTO) {
        AdminUserDTO entDto = new AdminUserDTO();
        BeanUtils.copyProperties(reqDTO, entDto);
        adminUserData.add(entDto);

        AdminUserAddVO retVo = new AdminUserAddVO();
        retVo.setId(entDto.getId());

        return retVo;
    }

    @Override
    public AdminUserUpdateVO update(AdminUserUpdateRequest reqDTO) {
        AdminUserUpdateVO retVo = new AdminUserUpdateVO();
        AdminUserDTO entDto = new AdminUserDTO();
        BeanUtils.copyProperties(reqDTO, entDto);

        adminUserData.updateById(entDto);

        return retVo;
    }

    @Override
    public AdminUserUpdateVO delete(AdminUserDeleteRequest reqDTO) {
        AdminUserUpdateVO retVo = new AdminUserUpdateVO();

        AdminUserDTO entDto = new AdminUserDTO();
        entDto.setDeleted(CommonConst.YES);

        adminUserData.updateById(entDto);

        return retVo;
    }

    @Override
    public AdminUserQueryVO query(AdminUserQueryRequest reqDTO) {
        AdminUserDTO entDTO = adminUserData.getById(reqDTO.getId());
        AdminUserQueryVO retVo = new AdminUserQueryVO();

        BeanUtils.copyProperties(entDTO, retVo);

        return retVo;
    }

    @Override
    public PageDTO<AdminUserQueryVO> getPageList(AdminUserListRequest reqDTO) {
        // return adminUserData.getPageList(reqDTO);
        return null;
    }
}

