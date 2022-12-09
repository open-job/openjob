package io.openjob.server.admin.service;

import io.openjob.server.admin.request.user.AdminRoleAddRequest;
import io.openjob.server.admin.request.user.AdminRoleDeleteRequest;
import io.openjob.server.admin.request.user.AdminRoleListRequest;
import io.openjob.server.admin.request.user.AdminRoleQueryRequest;
import io.openjob.server.admin.request.user.AdminRoleUpdateRequest;
import io.openjob.server.admin.vo.user.AdminRoleAddVO;
import io.openjob.server.admin.vo.user.AdminRoleQueryVO;
import io.openjob.server.admin.vo.user.AdminRoleUpdateVO;
import io.openjob.server.common.dto.PageDTO;

/**
 * @author inhere
 * @since 1.0.0
 */
public interface AdminRoleService {

    /**
     * Add AdminRole
     *
     * @param reqDTO reqDTO
     * @return AdminRoleAddVO
     */
    AdminRoleAddVO add(AdminRoleAddRequest reqDTO);

    /**
     * Get one AdminRole
     *
     * @param reqDTO reqDTO
     * @return AdminRoleQueryVO
     */
    AdminRoleQueryVO query(AdminRoleQueryRequest reqDTO);

    /**
     * Update one AdminRole
     *
     * @param reqDTO reqDTO
     * @return AdminRoleUpdateVO
     */
    AdminRoleUpdateVO update(AdminRoleUpdateRequest reqDTO);

    /**
     * Delete one AdminRole
     *
     * @param reqDTO reqDTO
     * @return AdminRoleUpdateVO
     */
    AdminRoleUpdateVO delete(AdminRoleDeleteRequest reqDTO);

    /**
     * Get page list AdminRole
     *
     * @param reqDTO reqDTO
     * @return AdminRoleListVO
     */
    PageDTO<AdminRoleQueryVO> getPageList(AdminRoleListRequest reqDTO);
}

