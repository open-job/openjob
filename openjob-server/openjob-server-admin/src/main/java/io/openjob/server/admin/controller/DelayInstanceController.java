package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.delay.DeleteDelayInstanceRequest;
import io.openjob.server.admin.request.delay.DeleteDelayRequest;
import io.openjob.server.admin.request.delay.ListDelayInstanceRequest;
import io.openjob.server.admin.request.delay.StopDelayInstanceRequest;
import io.openjob.server.admin.vo.delay.DeleteDelayInstanceVO;
import io.openjob.server.admin.service.DelayInstanceService;
import io.openjob.server.admin.vo.delay.ListDelayInstanceVO;
import io.openjob.server.admin.vo.delay.StopDelayInstanceVO;
import io.openjob.server.common.vo.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Api(value = "Delay Instance", tags = "Delay Instance")
@RestController
@RequestMapping("/admin/delay-instance")
public class DelayInstanceController {

    private final DelayInstanceService delayInstanceService;

    @Autowired
    public DelayInstanceController(DelayInstanceService delayInstanceService) {
        this.delayInstanceService = delayInstanceService;
    }

    @ApiOperation("List delay instance")
    @GetMapping("/list")
    public Result<PageVO<ListDelayInstanceVO>> list(@Valid @ModelAttribute ListDelayInstanceRequest request) {
        return Result.success(this.delayInstanceService.pageList(request));
    }

    @ApiOperation("Delete delay instance")
    @PostMapping("/delete")
    public Result<DeleteDelayInstanceVO> delete(@Valid @RequestBody DeleteDelayInstanceRequest request) {
        return Result.success(this.delayInstanceService.delete(request));
    }

    @ApiOperation("Delete delay instance")
    @PostMapping("/stop")
    public Result<StopDelayInstanceVO> stop(@Valid @RequestBody StopDelayInstanceRequest request) {
        return Result.success(this.delayInstanceService.stop(request));
    }
}
