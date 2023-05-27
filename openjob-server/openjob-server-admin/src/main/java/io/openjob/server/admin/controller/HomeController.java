package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.delay.AddDelayRequest;
import io.openjob.server.admin.vo.delay.AddDelayVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.2
 */
@Api(value = "Home", tags = "Home")
@RestController
@RequestMapping("/admin/home")
public class HomeController {
    @ApiOperation("One line data")
    @PostMapping("/tak-data")
    public Result<AddDelayVO> taskDay(@Valid @RequestBody AddDelayRequest addRequest) {
        return Result.success(null);
    }

    @ApiOperation("Two line data")
    @PostMapping("/system-data")
    public Result<AddDelayVO> systemData(@Valid @RequestBody AddDelayRequest addRequest) {
        return Result.success(null);
    }

    @ApiOperation("Job circle")
    @PostMapping("/job-circle")
    public Result<AddDelayVO> jobCircle(@Valid @RequestBody AddDelayRequest addRequest) {
        return Result.success(null);
    }

    @ApiOperation("Job percentage")
    @PostMapping("/job-percentage")
    public Result<AddDelayVO> jobPercentage(@Valid @RequestBody AddDelayRequest addRequest) {
        return Result.success(null);
    }

    @ApiOperation("Delay circle")
    @PostMapping("/delay-circle")
    public Result<AddDelayVO> delayCircle(@Valid @RequestBody AddDelayRequest addRequest) {
        return Result.success(null);
    }

    @ApiOperation("Delay percentage")
    @PostMapping("/delay-percentage")
    public Result<AddDelayVO> delayPercentage(@Valid @RequestBody AddDelayRequest addRequest) {
        return Result.success(null);
    }
}
