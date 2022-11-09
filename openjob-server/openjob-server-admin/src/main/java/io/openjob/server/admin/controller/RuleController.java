package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.AddAdminRuleRequest;
import io.openjob.server.admin.request.ListAdminRuleRequest;
import io.openjob.server.admin.request.UpdateAdminRuleRequest;
import io.openjob.server.admin.service.AdminRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author inhere
 * @date 2022-11-07 21:05:07
 * @since 1.0.0
 */
@Api(value = "AdminRule", tags = "AdminRule")
@RestController
@RequestMapping("/admin/xxx")
public class RuleController {

    private final AdminRuleService adminRuleService;

    @Autowired
    public RuleController(AdminRuleService adminRuleService) {
        this.adminRuleService = adminRuleService;
    }

    @ApiOperation("Add adminRule")
    @PostMapping("/add")
    public Result<AddAdminRuleVO> add(@Valid @RequestBody AddAdminRuleRequest addRequest) {
        return Result.success(this.adminRuleService.add(addRequest));
    }

    @ApiOperation("Update adminRule")
    @PostMapping("/update")
    public Result<UpdateAdminRuleVO> update(@Valid @RequestBody UpdateAdminRuleRequest updateRequest) {
        return Result.success(this.adminRuleService.update(updateRequest));
    }

    @ApiOperation("List adminRule")
    @PostMapping("/list")
    public Result<ListAdminRuleVO> list(@Valid @RequestBody ListAdminRuleRequest listRequest) {
        return Result.success(this.adminRuleService.list(listRequest));
    }
}

