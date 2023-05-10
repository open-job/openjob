package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.delay.AddDelayRequest;
import io.openjob.server.admin.request.delay.DeleteDelayRequest;
import io.openjob.server.admin.request.delay.ListDelayRequest;
import io.openjob.server.admin.request.delay.UpdateDelayRequest;
import io.openjob.server.admin.service.DelayService;
import io.openjob.server.admin.vo.delay.AddDelayVO;
import io.openjob.server.admin.vo.delay.DeleteDelayVO;
import io.openjob.server.admin.vo.delay.ListDelayVO;
import io.openjob.server.admin.vo.delay.UpdateDelayVO;
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
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Api(value = "Delay", tags = "Delay")
@RestController
@RequestMapping("/admin/delay")
public class DelayController {
    private final DelayService delayService;

    @Autowired
    public DelayController(DelayService delayService) {
        this.delayService = delayService;
    }

    @ApiOperation("Add delay")
    @PostMapping("/add")
    public Result<AddDelayVO> add(@Valid @RequestBody AddDelayRequest addRequest) {
        return Result.success(this.delayService.add(addRequest));
    }

    @ApiOperation("List delay")
    @GetMapping("/list")
    public Result<PageVO<ListDelayVO>> list(@Valid @ModelAttribute ListDelayRequest listDelayRequest) {
        return Result.success(this.delayService.list(listDelayRequest));
    }

    @ApiOperation("Delete delay")
    @PostMapping("/delete")
    public Result<DeleteDelayVO> delete(@Valid @RequestBody DeleteDelayRequest deleteDelayRequest) {
        return Result.success(this.delayService.delete(deleteDelayRequest));
    }

    @ApiOperation("Update delay")
    @PostMapping("/update")
    public Result<UpdateDelayVO> update(@Valid @RequestBody UpdateDelayRequest updateDelayRequest) {
        return Result.success(this.delayService.update(updateDelayRequest));
    }
}
