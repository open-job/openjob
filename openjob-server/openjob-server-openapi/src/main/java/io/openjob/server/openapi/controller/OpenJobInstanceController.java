package io.openjob.server.openapi.controller;

import io.openjob.common.response.Result;
import io.openjob.server.openapi.request.WorkerJobInstanceStatusRequest;
import io.openjob.server.openapi.vo.WorkerJobVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * When a client completes a task, batch reporting is used to report the task status.
     *
     * @param statusRequest status request.
     */
    @ApiOperation("update status")
    @PostMapping("/update-status")
    public Result<WorkerJobVO> handleStatus(WorkerJobInstanceStatusRequest statusRequest) {
        return Result.success(new WorkerJobVO());
    }
}
