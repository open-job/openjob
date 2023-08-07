package io.openjob.server.openapi.controller;

import io.openjob.common.response.Result;
import io.openjob.server.openapi.request.WorkerHeartbeatRequest;
import io.openjob.server.openapi.request.WorkerStartRequest;
import io.openjob.server.openapi.request.WorkerStopRequest;
import io.openjob.server.openapi.service.WorkerService;
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
 * @author zhenghongyang <sakuraovq@gmail.com>
 * @since 1.0.0
 */
@RestController
@Api(value = "Delay-instance", tags = "Delay-instance")
@RequestMapping("/openapi/worker")
@Slf4j
public class OpenWorkerController {

    private final WorkerService workerService;

    public OpenWorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @ApiOperation("Client needs to report client information every time it starts.")
    @PostMapping("/start")
    public Result<ServerWorkerStartVO> workerStart(@Valid @RequestBody WorkerStartRequest workerStartRequest) {
        ServerWorkerStartVO result = this.workerService.workerStart(workerStartRequest);

        log.info("Worker register success! address={}", workerStartRequest.getAddress());
        return Result.success(result);
    }

    @ApiOperation("Client needs to notify the server every time it goes offline.")
    @PostMapping("/stop")
    public Result<ServerWorkerStopVO> workerStop(WorkerStopRequest workerStopRequest) {
        ServerWorkerStopVO result = this.workerService.workerStop(workerStopRequest);

        log.info("Worker stop success! address={}", workerStopRequest.getAddress());
        return Result.success(result);
    }

    @ApiOperation("Worker heartbeat request.")
    @PostMapping("/heartbeat")
    public Result<ServerHeartbeatVO> heartbeat(WorkerHeartbeatRequest workerHeartbeatRequest) {
        ServerHeartbeatVO result = this.workerService.workerHeartbeat(workerHeartbeatRequest);
        return Result.success(result);
    }
}
