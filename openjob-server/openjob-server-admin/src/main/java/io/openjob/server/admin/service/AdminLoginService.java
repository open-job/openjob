package io.openjob.server.admin.service;

import io.openjob.server.admin.request.admin.AdminUserLoginRequest;
import io.openjob.server.admin.request.admin.AdminUserLogoutRequest;
import io.openjob.server.admin.request.admin.LoginUserInfoRequest;
import io.openjob.server.admin.request.admin.UpdateUserPasswordRequest;
import io.openjob.server.admin.vo.admin.AdminUserLoginVO;
import io.openjob.server.admin.vo.admin.AdminUserLogoutVO;
import io.openjob.server.admin.vo.admin.LoginUserInfoVO;
import io.openjob.server.admin.vo.admin.UpdateUserPasswordVO;

/**
 * @author inhere
 */
public interface AdminLoginService {

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
     * @param sessKey sessKey
     * @return vo
     */
    AdminUserLogoutVO logout(AdminUserLogoutRequest request, String sessKey);

    /**
     * Update user password
     *
     * @param request request
     * @return UpdateUserPasswordVO
     */
    UpdateUserPasswordVO updatePassword(UpdateUserPasswordRequest request);

    /**
     * login user info
     *
     * @param request request
     * @return vo
     */
    LoginUserInfoVO loginUserInfo(LoginUserInfoRequest request);
}