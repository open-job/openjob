package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.user.AdminRoleAddRequest;
import io.openjob.server.admin.request.user.AdminRoleDeleteRequest;
import io.openjob.server.admin.request.user.AdminRoleListRequest;
import io.openjob.server.admin.request.user.AdminRoleQueryRequest;
import io.openjob.server.admin.request.user.AdminRoleUpdateRequest;
import io.openjob.server.admin.service.AdminRoleService;
import io.openjob.server.admin.vo.user.AdminRoleAddVO;
import io.openjob.server.admin.vo.user.AdminRoleQueryVO;
import io.openjob.server.admin.vo.user.AdminRoleUpdateVO;
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
@Api(value = "AdminRole", tags = "AdminRole")
@RestController
@RequestMapping("/admin/roles")
public class RoleController {

    private final AdminRoleService adminRoleService;

    @Autowired
    public RoleController(AdminRoleService adminRoleService) {
        this.adminRoleService = adminRoleService;
    }

    @ApiOperation("Add a adminRole")
    @PostMapping("/add")
    public Result<AdminRoleAddVO> add(@Valid @RequestBody AdminRoleAddRequest addRequest) {
        return Result.success(this.adminRoleService.add(addRequest));
    }

    @ApiOperation("Update a adminRole")
    @PostMapping("/update")
    public Result<AdminRoleUpdateVO> update(@Valid @RequestBody AdminRoleUpdateRequest updateRequest) {
        return Result.success(this.adminRoleService.update(updateRequest));
    }

    @ApiOperation("Delete a adminRole")
    @PostMapping("/delete")
    public Result<AdminRoleUpdateVO> delete(@Valid @RequestBody AdminRoleDeleteRequest deleteRequest) {
        return Result.success(this.adminRoleService.delete(deleteRequest));
    }

    @ApiOperation("Get a adminRole")
    @GetMapping("/get")
    public Result<AdminRoleQueryVO> query(@Valid AdminRoleQueryRequest getRequest) {
        return Result.success(this.adminRoleService.query(getRequest));
    }

    @ApiOperation("List adminRole by page")
    @GetMapping("/list")
    public Result<PageDTO<AdminRoleQueryVO>> list(@Valid AdminRoleListRequest listRequest) {
        return Result.success(this.adminRoleService.getPageList(listRequest));
    }
}

