package io.openjob.server.openapi.controller;

import io.openjob.common.response.Result;
import io.openjob.server.openapi.request.WorkerJobInstanceTaskLogRequest;
import io.openjob.server.openapi.service.OpenTaskLogService;
import io.openjob.server.openapi.vo.WorkerJobInstanceTaskLogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenghongyang sakuraovq@gmail.com
 * @since 1.0.0
 */
@RestController
@Api(value = "Task-log", tags = "Task-log")
@RequestMapping("/openapi/task-log")
public class OpenTaskLogController {

    private final OpenTaskLogService openTaskLogService;

    @Autowired
    public OpenTaskLogController(OpenTaskLogService openTaskLogService) {
        this.openTaskLogService = openTaskLogService;
    }

    /**
     * During the execution of a task on the client, there is periodic real-time batch reporting of task logs.
     *
     * @param logRequest log request.
     */
    @ApiOperation("batch add")
    @PostMapping("/batch-add")
    public Result<WorkerJobInstanceTaskLogVO> batchAdd(WorkerJobInstanceTaskLogRequest logRequest) {
        return Result.success(this.openTaskLogService.batchAdd(logRequest));
    }
}
