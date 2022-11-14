package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.AdminMenuAddRequest;
import io.openjob.server.admin.request.AdminMenuDeleteRequest;
import io.openjob.server.admin.request.AdminMenuListRequest;
import io.openjob.server.admin.request.AdminMenuQueryRequest;
import io.openjob.server.admin.request.AdminMenuUpdateRequest;
import io.openjob.server.admin.service.AdminMenuService;
import io.openjob.server.admin.vo.AdminMenuAddVO;
import io.openjob.server.admin.vo.AdminMenuQueryVO;
import io.openjob.server.admin.vo.AdminMenuUpdateVO;
import io.openjob.server.common.dto.PageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author inhere
 * @date 2022-11-14 11:11:17
 * @since 1.0.0
 */
@Api(value = "AdminMenu", tags = "AdminMenu")
@RestController
@RequestMapping("/admin/xxx")
public class AdminMenuController {

    private final AdminMenuService adminMenuService;

    @Autowired
    public AdminMenuController(AdminMenuService adminMenuService) {
        this.adminMenuService = adminMenuService;
    }

    @ApiOperation("Add adminMenu")
    @PostMapping("/add")
    public Result<AdminMenuAddVO> add(@Valid @RequestBody AdminMenuAddRequest addRequest) {
        return Result.success(this.adminMenuService.add(addRequest));
    }

    @ApiOperation("Update adminMenu")
    @PostMapping("/update")
    public Result<AdminMenuUpdateVO> update(@Valid @RequestBody AdminMenuUpdateRequest updateRequest) {
        return Result.success(this.adminMenuService.update(updateRequest));
    }

    @ApiOperation("Delete a adminMenu")
    @PostMapping("/delete")
    public Result<AdminMenuUpdateVO> delete(@Valid @RequestBody AdminMenuDeleteRequest deleteRequest) {
        return Result.success(this.adminMenuService.delete(deleteRequest));
    }

    @ApiOperation("Get a adminMenu")
    @PostMapping("/get")
    public Result<AdminMenuQueryVO> query(@Valid @RequestBody AdminMenuQueryRequest getRequest) {
        return Result.success(this.adminMenuService.query(getRequest));
    }

    @ApiOperation("List adminMenu")
    @PostMapping("/list")
    public Result<PageDTO<AdminMenuQueryVO>> list(@Valid @RequestBody AdminMenuListRequest listRequest) {
        return Result.success(this.adminMenuService.getPageList(listRequest));
    }
}

