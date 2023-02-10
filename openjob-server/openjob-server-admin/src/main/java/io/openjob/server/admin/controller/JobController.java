package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.job.AddJobRequest;
import io.openjob.server.admin.request.job.ListJobRequest;
import io.openjob.server.admin.request.job.UpdateJobRequest;
import io.openjob.server.admin.request.job.UpdateJobStatusRequest;
import io.openjob.server.admin.service.JobService;
import io.openjob.server.admin.vo.job.AddJobVO;
import io.openjob.server.admin.vo.job.ListJobVO;
import io.openjob.server.admin.vo.job.UpdateJobStatusVO;
import io.openjob.server.admin.vo.job.UpdateJobVO;
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
@Api(value = "Job", tags = "Job")
@RestController
@RequestMapping("/admin/job")
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @ApiOperation("Add job")
    @PostMapping("/add")
    public Result<AddJobVO> add(@Valid @RequestBody AddJobRequest addJobRequest) {
        return Result.success(jobService.add(addJobRequest));
    }

    @ApiOperation("Update job")
    @PostMapping("/update")
    public Result<UpdateJobVO> update(@Valid @RequestBody UpdateJobRequest updateJobRequest) {
        return Result.success(jobService.update(updateJobRequest));
    }

    @ApiOperation("Update job status")
    @PostMapping("/update-status")
    public Result<UpdateJobStatusVO> updateStatus(@Valid @RequestBody UpdateJobStatusRequest updateJobStatusRequest) {
        return Result.success(jobService.updateStatus(updateJobStatusRequest));
    }

    @ApiOperation("List jobs")
    @PostMapping("/list")
    public Result<ListJobVO> listJob(@Valid @RequestBody ListJobRequest listJobRequest) {
        return Result.success(jobService.list(listJobRequest));
    }
}
