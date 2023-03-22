package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.system.SystemUpdateRequest;
import io.openjob.server.admin.service.SystemService;
import io.openjob.server.admin.vo.system.SystemUpdateVO;
import io.openjob.server.admin.vo.system.SystemVO;
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
 * @author riki
 * @since 1.0.0
 */
@Api(value = "System", tags = "System")
@RestController
@RequestMapping("/admin/system")
public class SystemController {

    private final SystemService systemService;

    @Autowired
    public SystemController(SystemService systemService) {
        this.systemService = systemService;
    }

    @ApiOperation("Get the latest System information")
    @GetMapping("/config")
    public Result<SystemVO> getLatest() {
        return Result.success(this.systemService.getLatest());
    }

    @ApiOperation("Update the System information")
    @PostMapping("/update")
    public Result<SystemUpdateVO> update(@Valid @RequestBody SystemUpdateRequest request) {
        return Result.success(this.systemService.update(request));
    }
}
