package io.openjob.server.openapi.controller;

import io.openjob.common.request.ServerCheckTaskMasterRequest;
import io.openjob.common.response.Result;
import io.openjob.common.response.WorkerResponse;
import io.openjob.server.openapi.request.ServerStopJobInstanceRequest;
import io.openjob.server.openapi.request.ServerSubmitJobInstanceRequest;
import io.openjob.server.openapi.vo.JobInstanceVO;
import io.openjob.server.openapi.vo.ServerSubmitJobInstanceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author zhenghongyang <sakuraovq@gmail.com>
 * @since 1.0.0
 */
@RestController
@Api(value = "task-master", tags = "task-master")
@RequestMapping("/openapi/task-master")
public class TaskMasterController {

    /**
     * When a scheduled task is triggered, the server will notify the client in real-time.
     *
     * @param request Server submit job instance request
     * @return Result
     */
    @ApiOperation("add")
    @PostMapping("/add")
    public Result<ServerSubmitJobInstanceVO> submitJobInstance(@Valid @RequestBody ServerSubmitJobInstanceRequest request) {
        return Result.success(new ServerSubmitJobInstanceVO());
    }

    @ApiOperation("stop-job-instance")
    @PostMapping("/stop-job-instance")
    public Result<JobInstanceVO> stopJobInstance(ServerStopJobInstanceRequest stopReq) {
        return Result.success(new JobInstanceVO());
    }

    /**
     * In certain specific scenarios,
     * the server needs to check if the client's task instances are still active.
     * This may involve performing certain operations or checks
     * on the server side to ensure that the connection with the client is active and valid.
     *
     * @param checkRequest check request
     */
    @ApiOperation("check-job-instance")
    @PostMapping("/check-job-instance")
    public void checkJobInstance(ServerCheckTaskMasterRequest checkRequest) {

    }
}
