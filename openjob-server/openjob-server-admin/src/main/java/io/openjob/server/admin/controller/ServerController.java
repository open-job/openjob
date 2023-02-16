package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.server.JobSlotRequest;
import io.openjob.server.admin.request.server.ServerListRequest;
import io.openjob.server.admin.service.ServerService;
import io.openjob.server.admin.vo.server.JobSlotListVO;
import io.openjob.server.admin.vo.server.ServerListVO;
import io.openjob.server.common.vo.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author riki
 * @since 1.0.0
 */
@Api(value = "Server", tags = "Server")
@RestController
@RequestMapping("/admin/server")
public class ServerController {

    private final ServerService serverService;

    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }


    @ApiOperation("List Server by page")
    @GetMapping("/list-server")
    public Result<PageVO<ServerListVO>> listServer(@Valid @ModelAttribute ServerListRequest request) {
        return Result.success(this.serverService.getServerList(request));
    }

    @ApiOperation("List job slot by page")
    @GetMapping("/list-job-slots")
    public Result<PageVO<JobSlotListVO>> listJobSlots(@Valid @ModelAttribute JobSlotRequest request) {
        return Result.success(this.serverService.getJobSlotsList(request));
    }
}
