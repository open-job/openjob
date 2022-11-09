package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.AddJobAdminUserRequest;
import io.openjob.server.admin.request.ListJobAdminUserRequest;
import io.openjob.server.admin.request.UpdateJobAdminUserRequest;
import io.openjob.server.admin.service.AdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author inhere
 * @date 2022-11-07 20:29:04
 * @since 1.0.0
 */
@Api(value = "AdminUser", tags = "AdminUser")
@RestController
@RequestMapping("/admin/users")
public class UserController {

    private final AdminUserService adminUserService;

    @Autowired
    public UserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @ApiOperation("Add AdminUser")
    @PostMapping("/add")
    public Result<AddAdminUserVO> add(@Valid @RequestBody AddAdminUserRequest addRequest) {
        return Result.success(this.adminUserService.add(addRequest));
    }

    @ApiOperation("Update AdminUser")
    @PostMapping("/update")
    public Result<UpdateAdminUserVO> update(@Valid @RequestBody UpdateAdminUserRequest updateRequest) {
        return Result.success(this.adminUserService.update(updateRequest));
    }

    @ApiOperation("List AdminUser")
    @PostMapping("/list")
    public Result<ListAdminUserVO> list(@Valid @RequestBody ListAdminUserRequest listRequest) {
        return Result.success(this.adminUserService.list(listRequest));
    }
}

