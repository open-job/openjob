package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.menu.AdminMenuAddRequest;
import io.openjob.server.admin.request.menu.AdminMenuDeleteRequest;
import io.openjob.server.admin.request.menu.AdminMenuListRequest;
import io.openjob.server.admin.request.menu.AdminMenuQueryRequest;
import io.openjob.server.admin.request.menu.AdminMenuUpdateRequest;
import io.openjob.server.admin.service.AdminMenuService;
import io.openjob.server.admin.vo.menu.AdminMenuAddVO;
import io.openjob.server.admin.vo.menu.AdminMenuQueryVO;
import io.openjob.server.admin.vo.menu.AdminMenuUpdateVO;
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
 *
 * @since 1.0.0
 */
@Api(value = "AdminMenu", tags = "AdminMenu")
@RestController
@RequestMapping("/admin/menu")
public class MenuController {

    private final AdminMenuService adminMenuService;

    @Autowired
    public MenuController(AdminMenuService adminMenuService) {
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
    @GetMapping("/get")
    public Result<AdminMenuQueryVO> query(@Valid @RequestBody AdminMenuQueryRequest getRequest) {
        return Result.success(this.adminMenuService.query(getRequest));
    }

    @ApiOperation("List adminMenu")
    @GetMapping("/list")
    public Result<PageDTO<AdminMenuQueryVO>> list(@Valid @RequestBody AdminMenuListRequest listRequest) {
        return Result.success(this.adminMenuService.getPageList(listRequest));
    }
}

