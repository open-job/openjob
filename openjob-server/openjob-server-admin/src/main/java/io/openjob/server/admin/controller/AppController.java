package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.app.AddAppRequest;
import io.openjob.server.admin.request.app.DeleteAppRequest;
import io.openjob.server.admin.request.app.ListAppRequest;
import io.openjob.server.admin.request.app.UpdateAppRequest;
import io.openjob.server.admin.service.AppService;
import io.openjob.server.admin.vo.app.AddAppVO;
import io.openjob.server.admin.vo.app.DeleteAppVO;
import io.openjob.server.admin.vo.app.ListAppVO;
import io.openjob.server.admin.vo.app.UpdateAppVO;
import io.openjob.server.common.vo.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @ApiOperation("Delete application")
    @PostMapping("/delete")
    public Result<DeleteAppVO> delete(@Valid @RequestBody DeleteAppRequest deleteAppRequest) {
        return Result.success(this.appService.delete(deleteAppRequest));
    }

    @ApiOperation("List application")
    @GetMapping("/list")
    public Result<PageVO<ListAppVO>> list(@Valid @ModelAttribute ListAppRequest listRequest) {
        return Result.success(this.appService.list(listRequest));
    }
}
