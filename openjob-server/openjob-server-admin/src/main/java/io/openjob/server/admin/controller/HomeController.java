package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.home.DelayCircleRequest;
import io.openjob.server.admin.request.home.DelayPercentageRequest;
import io.openjob.server.admin.request.home.JobCircleRequest;
import io.openjob.server.admin.request.home.JobPercentageRequest;
import io.openjob.server.admin.request.home.SystemDataRequest;
import io.openjob.server.admin.request.home.TaskDataRequest;
import io.openjob.server.admin.service.HomeService;
import io.openjob.server.admin.vo.home.DelayCircleVO;
import io.openjob.server.admin.vo.home.DelayPercentageVO;
import io.openjob.server.admin.vo.home.JobCircleVO;
import io.openjob.server.admin.vo.home.JobPercentageVO;
import io.openjob.server.admin.vo.home.SystemDataVO;
import io.openjob.server.admin.vo.home.TaskDataVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    private final HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @ApiOperation("One line data")
    @GetMapping("/task-data")
    public Result<TaskDataVO> taskData(@Valid @ModelAttribute TaskDataRequest taskDataRequest) {
        return Result.success(this.homeService.taskData(taskDataRequest));
    }

    @ApiOperation("Two line data")
    @GetMapping("/system-data")
    public Result<SystemDataVO> systemData(@Valid @ModelAttribute SystemDataRequest systemDataRequest) {
        return Result.success(this.homeService.systemData(systemDataRequest));
    }

    @ApiOperation("Job circle")
    @GetMapping("/job-circle")
    public Result<JobCircleVO> jobCircle(@Valid @ModelAttribute JobCircleRequest jobCircleRequest) {
        return Result.success(this.homeService.jobCircle(jobCircleRequest));
    }

    @ApiOperation("Job percentage")
    @GetMapping("/job-percentage")
    public Result<JobPercentageVO> jobPercentage(@Valid @ModelAttribute JobPercentageRequest jobPercentageRequest) {
        return Result.success(this.homeService.jobPercentage(jobPercentageRequest));
    }

    @ApiOperation("Delay circle")
    @GetMapping("/delay-circle")
    public Result<DelayCircleVO> delayCircle(@Valid @ModelAttribute DelayCircleRequest delayCircleRequest) {
        return Result.success(this.homeService.delayCircle(delayCircleRequest));
    }

    @ApiOperation("Delay percentage")
    @GetMapping("/delay-percentage")
    public Result<DelayPercentageVO> delayPercentage(@Valid @ModelAttribute DelayPercentageRequest delayPercentageRequest) {
        return Result.success(this.homeService.delayPercentage(delayPercentageRequest));
    }
}
