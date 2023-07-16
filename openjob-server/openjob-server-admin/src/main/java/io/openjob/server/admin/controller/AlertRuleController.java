package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.alert.AddAlertRuleRequest;
import io.openjob.server.admin.request.alert.DeleteAlertRuleRequest;
import io.openjob.server.admin.request.alert.ListAlertRuleRequest;
import io.openjob.server.admin.request.alert.UpdateAlertRuleRequest;
import io.openjob.server.admin.request.alert.UpdateAlertRuleStatusRequest;
import io.openjob.server.admin.service.AlertRuleService;
import io.openjob.server.admin.vo.alert.AddAlertRuleVO;
import io.openjob.server.admin.vo.alert.DeleteAlertRuleVO;
import io.openjob.server.admin.vo.alert.ListAlertRuleVO;
import io.openjob.server.admin.vo.alert.UpdateAlertRuleStatusVO;
import io.openjob.server.admin.vo.alert.UpdateAlertRuleVO;
import io.openjob.server.common.vo.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.5
 */
@Api(value = "Alert Rule", tags = "AlertRule")
@RestController
@RequestMapping("/admin/alert-rule")
public class AlertRuleController {

    private final AlertRuleService alertRuleService;

    @Autowired
    public AlertRuleController(AlertRuleService alertRuleService) {
        this.alertRuleService = alertRuleService;
    }

    @ApiOperation("Add")
    @PostMapping("/add")
    public Result<AddAlertRuleVO> add(AddAlertRuleRequest request) {
        return Result.success(this.alertRuleService.add(request));
    }

    @ApiOperation("Delete")
    @PostMapping("/delete")
    public Result<DeleteAlertRuleVO> delete(DeleteAlertRuleRequest request) {
        return Result.success(this.alertRuleService.delete(request));
    }

    @ApiOperation("Update")
    @PostMapping("/update")
    public Result<UpdateAlertRuleVO> update(UpdateAlertRuleRequest request) {
        return Result.success(this.alertRuleService.update(request));
    }

    @ApiOperation("Update status")
    @PostMapping("/update-status")
    public Result<UpdateAlertRuleStatusVO> updateStatus(UpdateAlertRuleStatusRequest request) {
        return Result.success(this.alertRuleService.updateStatus(request));
    }

    @ApiOperation("List")
    @PostMapping("/list")
    public Result<PageVO<ListAlertRuleVO>> list(ListAlertRuleRequest request) {
        return Result.success(this.alertRuleService.list(request));
    }
}
