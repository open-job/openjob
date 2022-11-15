package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.notify.NotifyGroupAddRequest;
import io.openjob.server.admin.request.notify.NotifyGroupListRequest;
import io.openjob.server.admin.request.notify.NotifyGroupQueryRequest;
import io.openjob.server.admin.request.notify.NotifyGroupUpdateRequest;
import io.openjob.server.admin.request.notify.NotifyGroupDeleteRequest;
import io.openjob.server.admin.service.NotifyGroupService;
import io.openjob.server.admin.vo.notify.NotifyGroupAddVO;
import io.openjob.server.admin.vo.notify.NotifyGroupQueryVO;
import io.openjob.server.admin.vo.notify.NotifyGroupUpdateVO;
import io.openjob.server.common.dto.PageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author inhere
 * @date 2022-11-15 14:19:59
 * @since 1.0.0
 */
@Api(value = "NotifyGroup", tags = "NotifyGroup")
@RestController
@RequestMapping("/admin/notify-groups")
public class NotifyGroupController {

    private final NotifyGroupService notifyGroupService;

    @Autowired
    public NotifyGroupController(NotifyGroupService notifyGroupService) {
        this.notifyGroupService = notifyGroupService;
    }

    @ApiOperation("Add a notifyGroup")
    @PostMapping("/add")
    public Result<NotifyGroupAddVO> add(@Valid @RequestBody NotifyGroupAddRequest addRequest) {
        return Result.success(this.notifyGroupService.add(addRequest));
    }

    @ApiOperation("Update a notifyGroup")
    @PostMapping("/update")
    public Result<NotifyGroupUpdateVO> update(@Valid @RequestBody NotifyGroupUpdateRequest updateRequest) {
        return Result.success(this.notifyGroupService.update(updateRequest));
    }

    @ApiOperation("Delete a notifyGroup")
    @PostMapping("/delete")
    public Result<NotifyGroupUpdateVO> delete(@Valid @RequestBody NotifyGroupDeleteRequest deleteRequest) {
        return Result.success(this.notifyGroupService.delete(deleteRequest));
    }

    @ApiOperation("Get a notifyGroup")
    @GetMapping("/get")
    public Result<NotifyGroupQueryVO> query(@Valid @RequestBody NotifyGroupQueryRequest getRequest) {
        return Result.success(this.notifyGroupService.query(getRequest));
    }

    @ApiOperation("List notifyGroup by page")
    @GetMapping("/list")
    public Result<PageDTO<NotifyGroupQueryVO>> list(@Valid @RequestBody NotifyGroupListRequest listRequest) {
        return Result.success(this.notifyGroupService.getPageList(listRequest));
    }
}

