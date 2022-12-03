package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.user.AdminUserAddRequest;
import io.openjob.server.admin.request.user.AdminUserDeleteRequest;
import io.openjob.server.admin.request.user.AdminUserListRequest;
import io.openjob.server.admin.request.user.AdminUserQueryRequest;
import io.openjob.server.admin.request.user.AdminUserUpdateRequest;
import io.openjob.server.admin.service.AdminUserService;
import io.openjob.server.admin.vo.user.AdminUserAddVO;
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
    public Result<AdminUserAddVO> add(@Valid @RequestBody AdminUserAddRequest addRequest) {
        return Result.success(this.adminUserService.add(addRequest));
    }

    @ApiOperation("Update a AdminUser")
    @PostMapping("/update")
    public Result<AdminUserUpdateVO> update(@Valid @RequestBody AdminUserUpdateRequest updateRequest) {
        return Result.success(this.adminUserService.update(updateRequest));
    }

    @ApiOperation("Delete a AdminUser")
    @PostMapping("/delete")
    public Result<AdminUserUpdateVO> delete(@Valid @RequestBody AdminUserDeleteRequest deleteRequest) {
        return Result.success(this.adminUserService.delete(deleteRequest));
    }

    @ApiOperation("Get a AdminUser")
    @GetMapping("/get")
    public Result<AdminUserQueryVO> query(@Valid AdminUserQueryRequest getRequest) {
        return Result.success(this.adminUserService.query(getRequest));
    }

    @ApiOperation("List AdminUser by page")
    @GetMapping("/list")
    public Result<PageDTO<AdminUserQueryVO>> list(@Valid AdminUserListRequest listRequest) {
        return Result.success(this.adminUserService.getPageList(listRequest));
    }

}

