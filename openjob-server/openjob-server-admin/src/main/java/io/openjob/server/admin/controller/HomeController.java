package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.home.DelayChartRequest;
import io.openjob.server.admin.request.home.JobChartRequest;
import io.openjob.server.admin.request.home.SystemDataRequest;
import io.openjob.server.admin.request.home.TaskDataRequest;
import io.openjob.server.admin.service.HomeService;
import io.openjob.server.admin.vo.home.DelayChartVO;
import io.openjob.server.admin.vo.home.JobChartVO;
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

    @ApiOperation("Job chart")
    @GetMapping("/job-chart")
    public Result<JobChartVO> jobChart(@Valid @ModelAttribute JobChartRequest jobChartRequest) {
        return Result.success(this.homeService.jobChart(jobChartRequest));
    }

    @ApiOperation("Delay chart")
    @GetMapping("/delay-chart")
    public Result<DelayChartVO> delayChart(@Valid @ModelAttribute DelayChartRequest delayChartRequest) {
        return Result.success(this.homeService.delayChart(delayChartRequest));
    }
}
