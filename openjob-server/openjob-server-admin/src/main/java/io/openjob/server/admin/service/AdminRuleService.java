package io.openjob.server.admin.service;

import io.openjob.server.admin.request.user.AdminRuleAddRequest;
import io.openjob.server.admin.request.user.AdminRuleDeleteRequest;
import io.openjob.server.admin.request.user.AdminRuleListRequest;
import io.openjob.server.admin.request.user.AdminRuleQueryRequest;
import io.openjob.server.admin.request.user.AdminRuleUpdateRequest;
import io.openjob.server.admin.vo.user.AdminRuleAddVO;
import io.openjob.server.admin.vo.user.AdminRuleQueryVO;
import io.openjob.server.admin.vo.user.AdminRuleUpdateVO;
import io.openjob.server.common.dto.PageDTO;

/**
 * @author inhere
 * @since 1.0.0
 */
public interface AdminRuleService {

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
     * @return AdminRuleQueryVO
     */
    AdminRuleQueryVO query(AdminRuleQueryRequest reqDTO);

    /**
     * Update one AdminRule
     *
     * @param reqDTO reqDTO
     * @return AdminRuleUpdateVO
     */
    AdminRuleUpdateVO update(AdminRuleUpdateRequest reqDTO);

    /**
     * Delete one AdminRule
     *
     * @param reqDTO reqDTO
     * @return AdminRuleUpdateVO
     */
    AdminRuleUpdateVO delete(AdminRuleDeleteRequest reqDTO);

    /**
     * Get page list AdminRule
     *
     * @param reqDTO reqDTO
     * @return AdminRuleListVO
     */
    PageDTO<AdminRuleQueryVO> getPageList(AdminRuleListRequest reqDTO);
}

