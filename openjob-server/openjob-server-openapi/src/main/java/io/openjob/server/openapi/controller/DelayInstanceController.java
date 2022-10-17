package io.openjob.server.openapi.controller;

import io.openjob.common.response.Result;
import io.openjob.server.openapi.request.DelayInstanceAddRequest;
import io.openjob.server.openapi.service.DelayInstanceService;
import io.openjob.server.openapi.vo.DelayInstanceAddVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@RestController
@RequestMapping("/openapi/delay-instance")
public class DelayInstanceController {

    private final DelayInstanceService instanceService;

    @Autowired
    public DelayInstanceController(DelayInstanceService instanceService) {
        this.instanceService = instanceService;
    }

    @PostMapping("/add")
    public Result<DelayInstanceAddVO> add(@Valid @RequestBody DelayInstanceAddRequest addRequest) {
        return Result.success(this.instanceService.add(addRequest));
    }
}
