package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.task.ListChildTaskRequest;
import io.openjob.server.admin.request.task.ListTaskLogRequest;
import io.openjob.server.admin.request.task.ListTaskRequest;
import io.openjob.server.admin.service.JobInstanceTaskService;
import io.openjob.server.admin.vo.task.ListChildTaskVO;
import io.openjob.server.admin.vo.task.ListTaskLogVO;
import io.openjob.server.admin.vo.task.ListTaskVO;
import io.openjob.server.common.vo.PageVO;
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
 * @since 1.0.7
 */
@Api(value = "Instance task", tags = "Instance task")
@RestController
@RequestMapping("/admin/instance-task")
public class JobInstanceTaskController {

    private final JobInstanceTaskService jobInstanceTaskService;

    @Autowired
    public JobInstanceTaskController(JobInstanceTaskService jobInstanceTaskService) {
        this.jobInstanceTaskService = jobInstanceTaskService;
    }

    @ApiOperation("List task")
    @GetMapping("/list")
    public Result<PageVO<ListTaskVO>> listSecond(@Valid @ModelAttribute ListTaskRequest listTaskRequest) {
        return Result.success(this.jobInstanceTaskService.getTaskList(listTaskRequest));
    }

    @ApiOperation("List child")
    @GetMapping("/list-child")
    public Result<PageVO<ListChildTaskVO>> listSecond(@Valid @ModelAttribute ListChildTaskRequest listChildTaskRequest) {
        return Result.success(this.jobInstanceTaskService.getChildList(listChildTaskRequest));
    }

    @ApiOperation("List task log")
    @GetMapping("/list-task-log")
    public Result<ListTaskLogVO> listTaskLog(@Valid @ModelAttribute ListTaskLogRequest listTaskLogRequest) {
        return Result.success(this.jobInstanceTaskService.getTaskLogList(listTaskLogRequest));
    }
}
