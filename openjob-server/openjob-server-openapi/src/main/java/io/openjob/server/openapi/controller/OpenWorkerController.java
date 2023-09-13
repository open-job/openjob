package io.openjob.server.openapi.controller;

import io.openjob.common.response.Result;
import io.openjob.server.openapi.request.WorkerHeartbeatRequest;
import io.openjob.server.openapi.request.WorkerStartRequest;
import io.openjob.server.openapi.request.WorkerStopRequest;
import io.openjob.server.openapi.service.OpenapiWorkerService;
import io.openjob.server.openapi.vo.ServerHeartbeatVO;
import io.openjob.server.openapi.vo.ServerWorkerStartVO;
import io.openjob.server.openapi.vo.ServerWorkerStopVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author zhenghongyang sakuraovq@gmail.com
 * @since 1.0.0
 */
@RestController
@Api(value = "worker", tags = "worker")
@RequestMapping("/openapi/worker")
@Slf4j
public class OpenWorkerController {

    private final OpenapiWorkerService workerService;

    public OpenWorkerController(OpenapiWorkerService workerService) {
        this.workerService = workerService;
    }

    /**
     * Client needs to report client information every time it starts.
     *
     * @param workerStartRequest worker start request
     * @return Result
     */
    @ApiOperation("Worker start")
    @PostMapping("/start")
    public Result<ServerWorkerStartVO> workerStart(@Valid @RequestBody WorkerStartRequest workerStartRequest) {
        ServerWorkerStartVO result = this.workerService.workerStart(workerStartRequest);

        log.info("Worker register success! address={}", workerStartRequest.getAddress());
        return Result.success(result);
    }

    /**
     * Client needs to notify the server every time it goes offline.
     *
     * @param workerStopRequest worker stop request
     * @return Result
     */
    @ApiOperation("Worker stop")
    @PostMapping("/stop")
    public Result<ServerWorkerStopVO> workerStop(@Valid @RequestBody WorkerStopRequest workerStopRequest) {
        ServerWorkerStopVO result = this.workerService.workerStop(workerStopRequest);

        log.info("Worker stop success! address={}", workerStopRequest.getAddress());
        return Result.success(result);
    }

    @ApiOperation("worker heartbeat")
    @PostMapping("/heartbeat")
    public Result<ServerHeartbeatVO> heartbeat(@Valid @RequestBody WorkerHeartbeatRequest workerHeartbeatRequest) {
        ServerHeartbeatVO result = this.workerService.workerHeartbeat(workerHeartbeatRequest);
        return Result.success(result);
    }
}
