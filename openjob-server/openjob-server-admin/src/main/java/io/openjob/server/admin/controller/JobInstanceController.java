package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.job.DeleteJobInstanceRequest;
import io.openjob.server.admin.request.job.ListJobInstanceRequest;
import io.openjob.server.admin.request.job.ListProcessorLogRequest;
import io.openjob.server.admin.request.job.StopJobInstanceRequest;
import io.openjob.server.admin.service.JobInstanceService;
import io.openjob.server.admin.vo.job.DeleteJobInstanceVO;
import io.openjob.server.admin.vo.job.ListJobInstanceVO;
import io.openjob.server.admin.vo.job.ListProcessorLogVO;
import io.openjob.server.admin.vo.job.StopJobInstanceVO;
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
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Api(value = "JobInstance", tags = "JobInstance")
@RestController
@RequestMapping("/admin/job-instance")
public class JobInstanceController {
    private final JobInstanceService jobInstanceService;

    @Autowired
    public JobInstanceController(JobInstanceService jobInstanceService) {
        this.jobInstanceService = jobInstanceService;
    }

    @ApiOperation("List job instances")
    @GetMapping("/list")
    public Result<PageVO<ListJobInstanceVO>> listJobInstance(@Valid @ModelAttribute ListJobInstanceRequest listJobRequest) {
        return Result.success(this.jobInstanceService.getPageList(listJobRequest));
    }

    @ApiOperation("Stop job instances")
    @PostMapping("/stop")
    public Result<StopJobInstanceVO> stopJobInstance(@Valid @RequestBody StopJobInstanceRequest stopRequest) {
        return Result.success(this.jobInstanceService.stopInstance(stopRequest));
    }

    @ApiOperation("Delete job instances")
    @PostMapping("/delete")
    public Result<DeleteJobInstanceVO> stopJobInstance(@Valid @RequestBody DeleteJobInstanceRequest request) {
        return Result.success(this.jobInstanceService.deleteInstance(request));
    }

    @ApiOperation("List job instance log")
    @GetMapping("/list-processor-log")
    public Result<ListProcessorLogVO> listProcessorLog(@Valid @ModelAttribute ListProcessorLogRequest request) {
        return Result.success(this.jobInstanceService.getProcessorList(request));
    }
}
