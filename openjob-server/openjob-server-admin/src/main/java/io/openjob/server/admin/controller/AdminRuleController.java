package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.user.AdminRuleAddRequest;
import io.openjob.server.admin.request.user.AdminRuleDeleteRequest;
import io.openjob.server.admin.request.user.AdminRuleListRequest;
import io.openjob.server.admin.request.user.AdminRuleQueryRequest;
import io.openjob.server.admin.request.user.AdminRuleUpdateRequest;
import io.openjob.server.admin.service.AdminRuleService;
import io.openjob.server.admin.vo.user.AdminRuleAddVO;
import io.openjob.server.admin.vo.user.AdminRuleQueryVO;
import io.openjob.server.admin.vo.user.AdminRuleUpdateVO;
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
@Api(value = "AdminRule", tags = "AdminRule")
@RestController
@RequestMapping("/admin/rules")
public class AdminRuleController {

    private final AdminRuleService adminRuleService;

    @Autowired
    public AdminRuleController(AdminRuleService adminRuleService) {
        this.adminRuleService = adminRuleService;
    }

    @ApiOperation("Add a adminRule")
    @PostMapping("/add")
    public Result<AdminRuleAddVO> add(@Valid @RequestBody AdminRuleAddRequest addRequest) {
        return Result.success(this.adminRuleService.add(addRequest));
    }

    @ApiOperation("Update a adminRule")
    @PostMapping("/update")
    public Result<AdminRuleUpdateVO> update(@Valid @RequestBody AdminRuleUpdateRequest updateRequest) {
        return Result.success(this.adminRuleService.update(updateRequest));
    }

    @ApiOperation("Delete a adminRule")
    @PostMapping("/delete")
    public Result<AdminRuleUpdateVO> delete(@Valid @RequestBody AdminRuleDeleteRequest deleteRequest) {
        return Result.success(this.adminRuleService.delete(deleteRequest));
    }

    @ApiOperation("Get a adminRule")
    @GetMapping("/get")
    public Result<AdminRuleQueryVO> query(@Valid AdminRuleQueryRequest getRequest) {
        return Result.success(this.adminRuleService.query(getRequest));
    }

    @ApiOperation("List adminRule by page")
    @GetMapping("/list")
    public Result<PageDTO<AdminRuleQueryVO>> list(@Valid AdminRuleListRequest listRequest) {
        return Result.success(this.adminRuleService.getPageList(listRequest));
    }
}

