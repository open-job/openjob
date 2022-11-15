package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.AdminConfigAddRequest;
import io.openjob.server.admin.request.AdminConfigListRequest;
import io.openjob.server.admin.request.AdminConfigQueryRequest;
import io.openjob.server.admin.request.AdminConfigUpdateRequest;
import io.openjob.server.admin.request.AdminConfigDeleteRequest;
import io.openjob.server.admin.service.AdminConfigService;
import io.openjob.server.admin.vo.AdminConfigAddVO;
import io.openjob.server.admin.vo.AdminConfigQueryVO;
import io.openjob.server.admin.vo.AdminConfigUpdateVO;
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
 * @date 2022-11-15 14:15:28
 * @since 1.0.0
 */
@Api(value = "AdminConfig", tags = "AdminConfig")
@RestController
@RequestMapping("/admin/config")
public class ConfigController {

    private final AdminConfigService adminConfigService;

    @Autowired
    public ConfigController(AdminConfigService adminConfigService) {
        this.adminConfigService = adminConfigService;
    }

    @ApiOperation("Add a adminConfig")
    @PostMapping("/add")
    public Result<AdminConfigAddVO> add(@Valid @RequestBody AdminConfigAddRequest addRequest) {
        return Result.success(this.adminConfigService.add(addRequest));
    }

    @ApiOperation("Update a adminConfig")
    @PostMapping("/update")
    public Result<AdminConfigUpdateVO> update(@Valid @RequestBody AdminConfigUpdateRequest updateRequest) {
        return Result.success(this.adminConfigService.update(updateRequest));
    }

    @ApiOperation("Delete a adminConfig")
    @PostMapping("/delete")
    public Result<AdminConfigUpdateVO> delete(@Valid @RequestBody AdminConfigDeleteRequest deleteRequest) {
        return Result.success(this.adminConfigService.delete(deleteRequest));
    }

    @ApiOperation("Get a adminConfig")
    @PostMapping("/get")
    public Result<AdminConfigQueryVO> query(@Valid @RequestBody AdminConfigQueryRequest getRequest) {
        return Result.success(this.adminConfigService.query(getRequest));
    }

    @ApiOperation("List adminConfig by page")
    @PostMapping("/list")
    public Result<PageDTO<AdminConfigQueryVO>> list(@Valid @RequestBody AdminConfigListRequest listRequest) {
        return Result.success(this.adminConfigService.getPageList(listRequest));
    }
}

