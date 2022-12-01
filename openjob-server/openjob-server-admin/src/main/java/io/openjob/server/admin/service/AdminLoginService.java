package io.openjob.server.admin.service;

import io.openjob.server.admin.request.user.AdminUserLoginRequest;
import io.openjob.server.admin.request.user.AdminUserLogoutRequest;
import io.openjob.server.admin.vo.user.AdminUserLoginVO;
import io.openjob.server.admin.vo.user.AdminUserLogoutVO;

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
}
