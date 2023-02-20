package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.job.ListJobInstanceRequest;
import io.openjob.server.admin.service.JobInstanceService;
import io.openjob.server.admin.vo.job.ListJobInstanceVO;
import io.openjob.server.common.vo.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author stelin <swoft@qq.com>
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
    public Result<PageVO<ListJobInstanceVO>> listJobInstance(@Valid @RequestBody ListJobInstanceRequest listJobRequest) {
        return Result.success(this.jobInstanceService.list(listJobRequest));
    }
}
