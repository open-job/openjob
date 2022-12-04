package io.openjob.server.admin.service.impl;

import io.openjob.common.constant.CommonConstant;
import io.openjob.server.admin.autoconfigure.AdminUserProperties;
import io.openjob.server.admin.request.user.AdminUserAddRequest;
import io.openjob.server.admin.request.user.AdminUserDeleteRequest;
import io.openjob.server.admin.request.user.AdminUserListRequest;
import io.openjob.server.admin.request.user.AdminUserQueryRequest;
import io.openjob.server.admin.request.user.AdminUserUpdateRequest;
import io.openjob.server.admin.service.AdminUserService;
import io.openjob.server.admin.vo.user.AdminUserAddVO;
import io.openjob.server.admin.vo.user.AdminUserQueryVO;
import io.openjob.server.admin.vo.user.AdminUserUpdateVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.HmacUtil;
import io.openjob.server.common.util.ObjectUtil;
import io.openjob.server.repository.data.AdminUserData;
import io.openjob.server.repository.dto.AdminUserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author inhere
 * @since 1.0.0
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    private final AdminUserData adminUserData;

    private final AdminUserProperties userProperties;

    @Autowired
    public AdminUserServiceImpl(AdminUserData adminUserData, AdminUserProperties userProperties) {
        this.adminUserData = adminUserData;
        this.userProperties = userProperties;
    }

    @Override
    public AdminUserAddVO add(AdminUserAddRequest reqDTO) {
        AdminUserDTO entDto = ObjectUtil.copyObject(reqDTO, new AdminUserDTO());

        // hash input passwd
        entDto.setPasswd(HmacUtil.hashPasswd(reqDTO.getPasswd(), userProperties.getPasswdSalt()));
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
        entDto.setDeleted(CommonConstant.YES);

        adminUserData.updateById(entDto);

        return retVo;
    }

    @Override
    public AdminUserQueryVO query(AdminUserQueryRequest reqDTO) {
        AdminUserDTO entDTO = adminUserData.getById(reqDTO.getId());

        return ObjectUtil.mapObject(entDTO, AdminUserQueryVO.class);
    }

    @Override
    public PageDTO<AdminUserQueryVO> getPageList(AdminUserListRequest reqDTO) {
        Page<AdminUserDTO> pageList = adminUserData.getPageList(reqDTO.getPage(), reqDTO.getSize());

        PageDTO<AdminUserQueryVO> pageVo = new PageDTO<>();
        pageVo.setTotal(pageList.getTotalElements());
        pageVo.setList(new ArrayList<>());

        pageList.forEach(entDTO -> {
            pageVo.getList().add(ObjectUtil.mapObject(entDTO, AdminUserQueryVO.class));
        });

        return pageVo;
    }

}

