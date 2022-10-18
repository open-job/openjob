package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Api(value = "job", tags = "job")
@RestController
@RequestMapping("/admin/job")
public class JobController {

    @ApiOperation("Add job")
    @PostMapping("/add")
    public Result<Object> add() {
        return Result.success(new Object());
    }
}
