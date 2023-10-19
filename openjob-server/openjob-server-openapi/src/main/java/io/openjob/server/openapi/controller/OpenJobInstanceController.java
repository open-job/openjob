package io.openjob.server.openapi.controller;

import io.openjob.common.response.Result;
import io.openjob.server.openapi.request.WorkerJobInstanceStatusRequest;
import io.openjob.server.openapi.request.WorkerJobInstanceTaskBatchRequest;
import io.openjob.server.openapi.service.OpenJobInstanceService;
import io.openjob.server.openapi.vo.WorkerJobInstanceStatusVO;
import io.openjob.server.openapi.vo.WorkerJobInstanceTaskBatchVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenghongyang sakuraovq@gmail.com
 * @since 1.0.0
 */
@RestController
@Api(value = "Job-instance", tags = "Job-instance")
@RequestMapping("/openapi/job-instance")
@Slf4j
public class OpenJobInstanceController {

    private final OpenJobInstanceService openJobInstanceService;

    @Autowired
    public OpenJobInstanceController(OpenJobInstanceService openJobInstanceService) {
        this.openJobInstanceService = openJobInstanceService;
    }

    @ApiOperation("Handle status")
    @PostMapping("/handle-status")
    public Result<WorkerJobInstanceStatusVO> handleStatus(WorkerJobInstanceStatusRequest statusRequest) {
        return Result.success(this.openJobInstanceService.handleStatus(statusRequest));
    }

    @ApiOperation("Handle tasks")
    @PostMapping("/handle-tasks")
    public Result<WorkerJobInstanceTaskBatchVO> handleTasks(WorkerJobInstanceTaskBatchRequest batchRequest) {
        return Result.success(this.openJobInstanceService.handleTasks(batchRequest));
    }
}
