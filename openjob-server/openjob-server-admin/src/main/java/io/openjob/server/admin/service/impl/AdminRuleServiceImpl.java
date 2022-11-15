package io.openjob.server.admin.service.impl;

import io.openjob.common.constant.CommonConst;
import io.openjob.server.admin.request.user.AdminRuleAddRequest;
import io.openjob.server.admin.request.user.AdminRuleDeleteRequest;
import io.openjob.server.admin.request.user.AdminRuleListRequest;
import io.openjob.server.admin.request.user.AdminRuleQueryRequest;
import io.openjob.server.admin.request.user.AdminRuleUpdateRequest;
import io.openjob.server.admin.service.AdminRuleService;
import io.openjob.server.admin.vo.user.AdminRuleAddVO;
import io.openjob.server.admin.vo.user.AdminRuleQueryVO;
import io.openjob.server.admin.vo.user.AdminRuleUpdateVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.data.AdminRuleData;
import io.openjob.server.repository.dto.AdminRuleDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author inhere
 * @date 2022-11-15 14:17:00
 * @since 1.0.0
 */
@Service
public class AdminRuleServiceImpl implements AdminRuleService {

    private final AdminRuleData adminRuleData;

    @Autowired
    public AdminRuleServiceImpl(AdminRuleData adminRuleData) {
        this.adminRuleData = adminRuleData;
    }

    @Override
    public AdminRuleAddVO add(AdminRuleAddRequest reqDTO) {
        AdminRuleDTO entDTO = new AdminRuleDTO();
        BeanUtils.copyProperties(reqDTO, entDTO);

        AdminRuleAddVO retVo = new AdminRuleAddVO();
        retVo.setId(adminRuleData.add(entDTO));

        return retVo;
    }

    @Override
    public AdminRuleUpdateVO update(AdminRuleUpdateRequest reqDTO) {
        AdminRuleDTO entDTO = new AdminRuleDTO();
        BeanUtils.copyProperties(reqDTO, entDTO);

        AdminRuleUpdateVO retVo = new AdminRuleUpdateVO();
        adminRuleData.updateById(entDTO);
        return retVo;
    }

    @Override
    public AdminRuleUpdateVO delete(AdminRuleDeleteRequest reqDTO) {
        AdminRuleDTO entDTO = new AdminRuleDTO();
        entDTO.setDeleted(CommonConst.YES);

        AdminRuleUpdateVO retVo = new AdminRuleUpdateVO();
        adminRuleData.updateById(entDTO);

        return retVo;
    }

    @Override
    public AdminRuleQueryVO query(AdminRuleQueryRequest reqDTO) {
        AdminRuleDTO entDTO = adminRuleData.getById(reqDTO.getId());
        AdminRuleQueryVO retVo = new AdminRuleQueryVO();

        BeanUtils.copyProperties(entDTO, retVo);

        return retVo;
    }

    @Override
    public PageDTO<AdminRuleQueryVO> getPageList(AdminRuleListRequest reqDTO) {
        // TODO
        return null;
    }
}

