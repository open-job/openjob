package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.constant.AdminConstant;
import io.openjob.server.admin.request.perm.AdminPermAddRequest;
import io.openjob.server.admin.request.perm.AdminPermDeleteRequest;
import io.openjob.server.admin.request.perm.AdminPermListRequest;
import io.openjob.server.admin.request.perm.AdminPermQueryRequest;
import io.openjob.server.admin.request.perm.AdminPermUpdateRequest;
import io.openjob.server.admin.request.perm.AdminPermissionMenusRequest;
import io.openjob.server.admin.service.AdminPermService;
import io.openjob.server.admin.vo.perm.AdminPermAddVO;
import io.openjob.server.admin.vo.perm.AdminPermQueryVO;
import io.openjob.server.admin.vo.perm.AdminPermUpdateVO;
import io.openjob.server.admin.vo.perm.AdminPermissionMenusVO;
import io.openjob.server.common.dto.PageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author inhere
 * @since 1.0.0
 */
@Api(value = "AdminPerm", tags = "AdminPerm")
@RestController
@RequestMapping("/admin/perms")
public class PermController {

    private final AdminPermService adminPermService;

    @Autowired
    public PermController(AdminPermService adminPermService) {
        this.adminPermService = adminPermService;
    }

    @ApiOperation("Add adminPerm")
    @PostMapping("/add")
    public Result<AdminPermAddVO> add(@Valid @RequestBody AdminPermAddRequest addRequest) {
        return Result.success(this.adminPermService.add(addRequest));
    }

    @ApiOperation("Update adminPerm")
    @PostMapping("/update")
    public Result<AdminPermUpdateVO> update(@Valid @RequestBody AdminPermUpdateRequest updateRequest) {
        return Result.success(this.adminPermService.update(updateRequest));
    }

    @ApiOperation("Delete a adminPerm")
    @PostMapping("/delete")
    public Result<AdminPermUpdateVO> delete(@Valid @RequestBody AdminPermDeleteRequest deleteRequest) {
        return Result.success(this.adminPermService.delete(deleteRequest));
    }

    @ApiOperation("Get a adminPerm")
    @GetMapping("/get")
    public Result<AdminPermQueryVO> query(@Valid @ModelAttribute AdminPermQueryRequest getRequest) {
        return Result.success(this.adminPermService.query(getRequest));
    }

    @ApiOperation("List adminPerm")
    @GetMapping("/list")
    public Result<PageDTO<AdminPermQueryVO>> list(@Valid @ModelAttribute AdminPermListRequest listRequest) {
        return Result.success(this.adminPermService.getPageList(listRequest));
    }

    @ApiOperation("List menus")
    @GetMapping("/menus")
    public Result<AdminPermissionMenusVO> menus(@Valid @ModelAttribute AdminPermissionMenusRequest listRequest, HttpServletRequest request) {
        listRequest.setUid((Long) request.getAttribute(AdminConstant.REQUEST_UID_KEY));
        return Result.success(this.adminPermService.getMenus(listRequest));
    }
}

