package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.constant.AdminConstant;
import io.openjob.server.admin.request.perm.AdminPermissionMenusRequest;
import io.openjob.server.admin.service.AdminPermService;
import io.openjob.server.admin.vo.perm.AdminPermissionMenusVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @ApiOperation("List menus")
    @GetMapping("/menus")
    public Result<AdminPermissionMenusVO> menus(@Valid @ModelAttribute AdminPermissionMenusRequest listRequest, HttpServletRequest request) {
        listRequest.setUid((Long) request.getAttribute(AdminConstant.REQUEST_UID_KEY));
        return Result.success(this.adminPermService.getMenus(listRequest));
    }
}

