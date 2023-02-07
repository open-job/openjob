package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.AddAppRequest;
import io.openjob.server.admin.request.ListAppRequest;
import io.openjob.server.admin.request.UpdateAppRequest;
import io.openjob.server.admin.service.AppService;
import io.openjob.server.admin.vo.AddAppVO;
import io.openjob.server.admin.vo.ListAppVO;
import io.openjob.server.admin.vo.UpdateAppVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Api(value = "App", tags = "App")
@RestController
@RequestMapping("/admin/app")
public class AppController {
    private final AppService appService;

    @Autowired
    public AppController(AppService appService) {
        this.appService = appService;
    }

    @ApiOperation("Add application")
    @PostMapping("/add")
    public Result<AddAppVO> add(@Valid @RequestBody AddAppRequest addRequest) {
        return Result.success(this.appService.add(addRequest));
    }

    @ApiOperation("Update application")
    @PostMapping("/update")
    public Result<UpdateAppVO> update(@Valid @RequestBody UpdateAppRequest updateRequest) {
        return Result.success(this.appService.update(updateRequest));
    }

    @ApiOperation("List application")
    @PostMapping("/list")
    public Result<ListAppVO> list(@Valid @RequestBody ListAppRequest listRequest) {
        return Result.success(this.appService.list(listRequest));
    }
}
