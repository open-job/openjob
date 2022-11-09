package io.openjob.server.admin.service;

import io.openjob.server.admin.entity.AdminRule;
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

/**
 * @author inhere
 * @date 2022-11-07 21:05:07
 * @since 1.0.0
 */
public interface AdminRuleService  {

    /**
     * Add AdminRule
     *
     * @param reqDTO reqDTO
     * @return AdminRuleAddVO
     */
    AdminRuleAddVO add(AdminRuleAddRequest reqDTO);

    /**
     * Get one AdminRule
     *
     * @param reqDTO reqDTO
     * @return AdminRuleDetailDTO
     */
    AdminRuleDetailDTO getById(AdminRuleQueryRequest reqDTO);

    /**
     * Update one AdminRule
     *
     * @param reqDTO reqDTO
     * @return AdminRuleUpdateVO
     */
    AdminRuleUpdateVO update(AdminRuleUpdateRequest reqDTO);

    /**
     * @param reqDTO reqDTO
     * @return AdminRuleUpdateVO
     */
    AdminRuleUpdateVO deleteById(AdminRuleDeleteRequest reqDTO);

    /**
     * Get page list AdminRule
     *
     * @param reqDTO reqDTO
     * @return AdminRuleListVO
     */
    PageDTO<AdminRuleListVO> getPageList(AdminRuleListRequest reqDTO);
}

