package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.worker.WorkerListRequest;
import io.openjob.server.admin.service.WorkerService;
import io.openjob.server.admin.vo.worker.WorkerListVO;
import io.openjob.server.common.vo.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author riki
 * @since 1.0.0
 */
@Api(value = "Worker", tags = "Worker")
@RestController
@RequestMapping("/admin/worker")
public class WorkerController {

    private final WorkerService workerService;

    @Autowired
    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @ApiOperation("List Worker by page")
    @GetMapping("/list")
    public Result<PageVO<WorkerListVO>> list(@ModelAttribute WorkerListRequest request) {
        return Result.success(workerService.getPage(request));
    }
}
