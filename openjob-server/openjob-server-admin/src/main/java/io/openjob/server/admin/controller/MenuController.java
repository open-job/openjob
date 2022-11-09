package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.AddJobAdminMenuRequest;
import io.openjob.server.admin.request.ListJobAdminMenuRequest;
import io.openjob.server.admin.request.UpdateJobAdminMenuRequest;
import io.openjob.server.admin.service.AdminMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author inhere
 * @date 2022-11-07 20:21:39
 * @since 1.0.0
 */
@Api(value = "JobAdminMenu", tags = "JobAdminMenu")
@RestController
@RequestMapping("/some-api")
public class MenuController {

    private final AdminMenuService jobAdminMenuService;

    @Autowired
    public MenuController(AdminMenuService jobAdminMenuService) {
        this.jobAdminMenuService = jobAdminMenuService;
    }

    @ApiOperation("Add jobAdminMenu")
    @PostMapping("/add")
    public Result<AddJobAdminMenuVO> add(@Valid @RequestBody AddJobAdminMenuRequest addRequest) {
        return Result.success(this.jobAdminMenuService.add(addRequest));
    }

    @ApiOperation("Update jobAdminMenu")
    @PostMapping("/update")
    public Result<UpdateJobAdminMenuVO> update(@Valid @RequestBody UpdateJobAdminMenuRequest updateRequest) {
        return Result.success(this.jobAdminMenuService.update(updateRequest));
    }

    @ApiOperation("List jobAdminMenu")
    @PostMapping("/list")
    public Result<ListJobAdminMenuVO> list(@Valid @RequestBody ListJobAdminMenuRequest listRequest) {
        return Result.success(this.jobAdminMenuService.list(listRequest));
    }
}

