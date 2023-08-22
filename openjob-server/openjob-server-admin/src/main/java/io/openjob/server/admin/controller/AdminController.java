package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.constant.AdminConstant;
import io.openjob.server.admin.request.admin.AdminUserLoginRequest;
import io.openjob.server.admin.request.admin.AdminUserLogoutRequest;
import io.openjob.server.admin.request.admin.LoginUserInfoRequest;
import io.openjob.server.admin.request.admin.UpdateUserPasswordRequest;
import io.openjob.server.admin.service.AdminLoginService;
import io.openjob.server.admin.vo.admin.AdminUserLoginVO;
import io.openjob.server.admin.vo.admin.AdminUserLogoutVO;
import io.openjob.server.admin.vo.admin.LoginUserInfoVO;
import io.openjob.server.admin.vo.admin.UpdateUserPasswordVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

/**
 * @author inhere
 * @since 1.0.0
 */
@Api(value = "AdminLogInOut", tags = "AdminLoginInOut")
@RestController
@RequestMapping("/admin/user")
public class AdminController {

    private final AdminLoginService adminLoginService;

    @Autowired
    public AdminController(AdminLoginService adminLoginService) {
        this.adminLoginService = adminLoginService;
    }

    @ApiOperation("User login")
    @PostMapping("/login")
    public Result<AdminUserLoginVO> login(@Valid @RequestBody AdminUserLoginRequest request) {
        return Result.success(this.adminLoginService.login(request));
    }

    /**
     * Login user info
     */
    @ApiOperation("Login user")
    @GetMapping("/user-info")
    public Result<LoginUserInfoVO> userInfo(@Valid @ModelAttribute LoginUserInfoRequest loginUserInfoRequest, HttpServletRequest request) {
        if (Objects.isNull(loginUserInfoRequest.getId())) {
            loginUserInfoRequest.setId((Long) request.getAttribute(AdminConstant.REQUEST_UID_KEY));
        }

        return Result.success(this.adminLoginService.loginUserInfo(loginUserInfoRequest));
    }

    /**
     * Login user info
     */
    @ApiOperation("Update password")
    @PostMapping("/update-password")
    public Result<UpdateUserPasswordVO> updatePassword(@Valid @RequestBody UpdateUserPasswordRequest updateUserPasswordRequest) {
        return Result.success(this.adminLoginService.updatePassword(updateUserPasswordRequest));
    }

    /**
     * Login out
     */
    @ApiOperation("User logout")
    @PostMapping("/logout")
    public Result<AdminUserLogoutVO> logout(@RequestBody(required = false) AdminUserLogoutRequest request, @RequestHeader HttpHeaders headers) {
        String sessKey = headers.getFirst(AdminConstant.HEADER_SESSION_KEY);

        return Result.success(this.adminLoginService.logout(request, sessKey));
    }

}

