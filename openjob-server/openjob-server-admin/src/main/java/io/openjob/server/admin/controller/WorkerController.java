package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.worker.WorkerListRequest;
import io.openjob.server.admin.service.WorkerService;
import io.openjob.server.admin.vo.worker.WorkerListVO;
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
@Api(value = "workerAdmin", tags = "workerAdmin")
@RestController
@RequestMapping("/admin/worker")
public class WorkerController {

    private final WorkerService workerService;


    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }


    @ApiOperation("List Worker by page")
    @GetMapping("/page")
    public Result<PageDTO<WorkerListVO>> list(@ModelAttribute WorkerListRequest request) {
        return Result.success(workerService.page(request));
    }


}
