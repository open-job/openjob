package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.user.AdminUserAddRequest;
import io.openjob.server.admin.request.user.AdminUserDeleteRequest;
import io.openjob.server.admin.request.user.AdminUserListRequest;
import io.openjob.server.admin.request.user.AdminUserLoginRequest;
import io.openjob.server.admin.request.user.AdminUserLogoutRequest;
import io.openjob.server.admin.request.user.AdminUserQueryRequest;
import io.openjob.server.admin.request.user.AdminUserUpdateRequest;
import io.openjob.server.admin.service.AdminUserService;
import io.openjob.server.admin.vo.user.AdminUserAddVO;
import io.openjob.server.admin.vo.user.AdminUserLoginVO;
import io.openjob.server.admin.vo.user.AdminUserLogoutVO;
import io.openjob.server.admin.vo.user.AdminUserQueryVO;
import io.openjob.server.admin.vo.user.AdminUserUpdateVO;
import io.openjob.server.common.dto.PageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author inhere
 * @date 2022-11-07 20:29:04
 * @since 1.0.0
 */
@Api(value = "AdminLogInOut", tags = "LogInOut")
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminUserService adminUserService;

    @Autowired
    public AdminController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @ApiOperation("admin user login")
    @PostMapping("/login")
    public Result<AdminUserLoginVO> login(@Valid @RequestBody AdminUserLoginRequest request) {
        return Result.success(this.adminUserService.login(request));
    }

    @ApiOperation("admin user logout")
    @PostMapping("/logout")
    public Result<AdminUserLogoutVO> logout(@Valid @RequestBody AdminUserLogoutRequest request) {
        return Result.success(this.adminUserService.logout(request));
    }

}

