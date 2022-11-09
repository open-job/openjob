package io.openjob.server.admin.service.impl;

import io.openjob.server.admin.entity.AdminRule;
import io.openjob.server.admin.manager.AdminRuleData;
import io.openjob.server.admin.service.AdminRuleService;
import io.openjob.server.admin.request.AdminRuleAddRequest;
import io.openjob.server.admin.vo.AdminRuleAddVO;
import io.openjob.server.admin.request.AdminRuleDetailRequest;
import io.openjob.server.admin.vo.AdminRuleDetailVO;
import io.openjob.server.admin.request.AdminRuleUpdateRequest;
import io.openjob.server.admin.vo.AdminRuleUpdateVO;
import io.openjob.server.admin.request.AdminRuleDeleteRequest;
import io.openjob.server.admin.vo.AdminRuleDeleteVO;
import io.openjob.server.admin.request.AdminRuleListRequest;
import io.openjob.server.admin.vo.AdminRuleListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author inhere
 * @date 2022-11-07 21:05:08
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
        return adminRuleData.add(reqDTO);
    }

    @Override
    public AdminRuleUpdateVO update(AdminRuleUpRequest reqDTO) {
        return adminRuleData.update(reqDTO);
    }

    @Override
    public AdminRuleUpdateVO deleteById(AdminRuleDelRequest reqDTO) {
        return adminRuleData.deleteById(reqDTO);
    }

    @Override
    public AdminRule getById(AdminRuleRequest reqDTO) {
        return adminRuleData.getById(reqDTO);
    }

    @Override
    public PageDTO<AdminRuleVO> getPageList(AdminRuleListRequest reqDTO) {
        return adminRuleData.getPageList(reqDTO);
    }
}

