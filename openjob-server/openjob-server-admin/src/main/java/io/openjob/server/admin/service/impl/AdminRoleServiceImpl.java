package io.openjob.server.admin.service.impl;

import io.openjob.common.constant.CommonConstant;
import io.openjob.server.admin.request.user.AdminRoleAddRequest;
import io.openjob.server.admin.request.user.AdminRoleDeleteRequest;
import io.openjob.server.admin.request.user.AdminRoleListRequest;
import io.openjob.server.admin.request.user.AdminRoleQueryRequest;
import io.openjob.server.admin.request.user.AdminRoleUpdateRequest;
import io.openjob.server.admin.service.AdminRoleService;
import io.openjob.server.admin.vo.user.AdminRoleAddVO;
import io.openjob.server.admin.vo.user.AdminRoleQueryVO;
import io.openjob.server.admin.vo.user.AdminRoleUpdateVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.repository.data.AdminRoleData;
import io.openjob.server.repository.dto.AdminRoleDTO;
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
public class AdminRoleServiceImpl implements AdminRoleService {

    private final AdminRoleData adminRoleData;

    @Autowired
    public AdminRoleServiceImpl(AdminRoleData adminRoleData) {
        this.adminRoleData = adminRoleData;
    }

    @Override
    public AdminRoleAddVO add(AdminRoleAddRequest reqDTO) {
        AdminRoleDTO entDTO = new AdminRoleDTO();
        BeanUtils.copyProperties(reqDTO, entDTO);

        AdminRoleAddVO retVo = new AdminRoleAddVO();
        retVo.setId(adminRoleData.add(entDTO));

        return retVo;
    }

    @Override
    public AdminRoleUpdateVO update(AdminRoleUpdateRequest reqDTO) {
        AdminRoleDTO entDTO = new AdminRoleDTO();
        BeanUtils.copyProperties(reqDTO, entDTO);

        AdminRoleUpdateVO retVo = new AdminRoleUpdateVO();
        adminRoleData.updateById(entDTO);
        return retVo;
    }

    @Override
    public AdminRoleUpdateVO delete(AdminRoleDeleteRequest reqDTO) {
        AdminRoleDTO entDTO = new AdminRoleDTO();
        entDTO.setId(reqDTO.getId());
        entDTO.setDeleted(CommonConstant.YES);

        AdminRoleUpdateVO retVo = new AdminRoleUpdateVO();
        adminRoleData.updateById(entDTO);

        return retVo;
    }

    @Override
    public AdminRoleQueryVO query(AdminRoleQueryRequest reqDTO) {
        AdminRoleDTO entDTO = adminRoleData.getById(reqDTO.getId());

        return BeanMapperUtil.map(entDTO, AdminRoleQueryVO.class);
    }

    @Override
    public PageDTO<AdminRoleQueryVO> getPageList(AdminRoleListRequest reqDTO) {
        Page<AdminRoleDTO> pageList = adminRoleData.getPageList(reqDTO.getPage(), reqDTO.getSize());

        PageDTO<AdminRoleQueryVO> pageVo = new PageDTO<>();
        pageVo.setTotal(pageList.getTotalElements());
        pageVo.setList(new ArrayList<>());

        pageList.forEach(entDTO -> {
            AdminRoleQueryVO retVo = new AdminRoleQueryVO();
            BeanUtils.copyProperties(entDTO, retVo);

            pageVo.getList().add(retVo);
        });

        return pageVo;
    }
}

