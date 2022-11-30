package io.openjob.server.admin.service;

import io.openjob.server.admin.request.user.AdminUserAddRequest;
import io.openjob.server.admin.request.user.AdminUserDeleteRequest;
import io.openjob.server.admin.request.user.AdminUserListRequest;
import io.openjob.server.admin.request.user.AdminUserLoginRequest;
import io.openjob.server.admin.request.user.AdminUserLogoutRequest;
import io.openjob.server.admin.request.user.AdminUserQueryRequest;
import io.openjob.server.admin.request.user.AdminUserUpdateRequest;
import io.openjob.server.admin.vo.user.AdminUserAddVO;
import io.openjob.server.admin.vo.user.AdminUserLoginVO;
import io.openjob.server.admin.vo.user.AdminUserLogoutVO;
import io.openjob.server.admin.vo.user.AdminUserQueryVO;
import io.openjob.server.admin.vo.user.AdminUserUpdateVO;
import io.openjob.server.common.dto.PageDTO;

/**
 * @author inhere
 * @since 1.0.0
 */
public interface AdminUserService {

    /**
     * Add AdminUser
     *
     * @param reqDTO reqDTO
     * @return AdminUserAddVO
     */
    AdminUserAddVO add(AdminUserAddRequest reqDTO);

    /**
     * Get one AdminUser
     *
     * @param reqDTO reqDTO
     * @return AdminUserDetailDTO
     */
    AdminUserQueryVO query(AdminUserQueryRequest reqDTO);

    /**
     * Update one AdminUser
     *
     * @param reqDTO reqDTO
     * @return AdminUserUpdateVO
     */
    AdminUserUpdateVO update(AdminUserUpdateRequest reqDTO);

    /**
     * Delete one
     *
     * @param reqDTO reqDTO
     * @return AdminUserUpdateVO
     */
    AdminUserUpdateVO delete(AdminUserDeleteRequest reqDTO);

    /**
     * Get page list AdminUser
     *
     * @param reqDTO reqDTO
     * @return AdminUserListVO
     */
    PageDTO<AdminUserQueryVO> getPageList(AdminUserListRequest reqDTO);

    /**
     * admin user login
     *
     * @param request request
     * @return vo
     */
    AdminUserLoginVO login(AdminUserLoginRequest request);

    /**
     * admin user logout
     *
     * @param request request
     * @return vo
     */
    AdminUserLogoutVO logout(AdminUserLogoutRequest request);
}

