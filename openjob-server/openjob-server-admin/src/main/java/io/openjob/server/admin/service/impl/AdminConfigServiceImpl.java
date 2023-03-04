package io.openjob.server.admin.service.impl;

import io.openjob.common.constant.CommonConstant;
import io.openjob.server.admin.request.admin.AdminConfigAddRequest;
import io.openjob.server.admin.request.admin.AdminConfigDeleteRequest;
import io.openjob.server.admin.request.admin.AdminConfigListRequest;
import io.openjob.server.admin.request.admin.AdminConfigQueryRequest;
import io.openjob.server.admin.request.admin.AdminConfigUpdateRequest;
import io.openjob.server.admin.service.AdminConfigService;
import io.openjob.server.admin.vo.admin.AdminConfigAddVO;
import io.openjob.server.admin.vo.admin.AdminConfigQueryVO;
import io.openjob.server.admin.vo.admin.AdminConfigUpdateVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.repository.data.AdminConfigData;
import io.openjob.server.repository.dto.AdminConfigDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author inhere
 * @since 1.0.0
 */
@Service
public class AdminConfigServiceImpl implements AdminConfigService {

    private final AdminConfigData adminConfigData;

    @Autowired
    public AdminConfigServiceImpl(AdminConfigData adminConfigData) {
        this.adminConfigData = adminConfigData;
    }

    @Override
    public AdminConfigAddVO add(AdminConfigAddRequest reqDTO) {
        AdminConfigDTO entDTO = new AdminConfigDTO();
        BeanUtils.copyProperties(reqDTO, entDTO);

        AdminConfigAddVO retVo = new AdminConfigAddVO();
        retVo.setId(adminConfigData.add(entDTO));

        return retVo;
    }

    @Override
    public AdminConfigUpdateVO update(AdminConfigUpdateRequest reqDTO) {
        AdminConfigDTO entDTO = new AdminConfigDTO();
        BeanUtils.copyProperties(reqDTO, entDTO);

        AdminConfigUpdateVO retVo = new AdminConfigUpdateVO();
        adminConfigData.updateById(entDTO);
        return retVo;
    }

    @Override
    public AdminConfigUpdateVO delete(AdminConfigDeleteRequest reqDTO) {
        AdminConfigDTO entDTO = new AdminConfigDTO();
        entDTO.setId(reqDTO.getId());
        entDTO.setDeleted(CommonConstant.YES);

        AdminConfigUpdateVO retVo = new AdminConfigUpdateVO();
        adminConfigData.updateById(entDTO);

        return retVo;
    }

    @Override
    public AdminConfigQueryVO query(AdminConfigQueryRequest reqDTO) {
        AdminConfigDTO entDTO = adminConfigData.getById(reqDTO.getId());

        return BeanMapperUtil.map(entDTO, AdminConfigQueryVO.class);
    }

    @Override
    public PageDTO<AdminConfigQueryVO> getPageList(AdminConfigListRequest reqDTO) {
        // TODO
        return null;
    }
}

