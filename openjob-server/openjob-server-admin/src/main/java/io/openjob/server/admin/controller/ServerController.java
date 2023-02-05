package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.server.ServerListRequest;
import io.openjob.server.admin.service.ServerService;
import io.openjob.server.admin.vo.server.ServerListVO;
import io.openjob.server.common.dto.PageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author riki
 * @date 2022-12-26
 */
@Api(value = "adminServer", tags = "adminServer")
@RestController
@RequestMapping("/admin/server")
public class ServerController {

    private final ServerService serverService;

    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }


    @ApiOperation("List Server by page")
    @GetMapping("/page")
    public Result<PageDTO<ServerListVO>> list(@ModelAttribute ServerListRequest request) {
        return Result.success(serverService.getPageList(request));
    }


}
