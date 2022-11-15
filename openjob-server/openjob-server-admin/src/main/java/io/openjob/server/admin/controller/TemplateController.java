package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.notify.NotifyTemplateAddRequest;
import io.openjob.server.admin.request.notify.NotifyTemplateListRequest;
import io.openjob.server.admin.request.notify.NotifyTemplateQueryRequest;
import io.openjob.server.admin.request.notify.NotifyTemplateUpdateRequest;
import io.openjob.server.admin.request.notify.NotifyTemplateDeleteRequest;
import io.openjob.server.admin.service.NotifyTemplateService;
import io.openjob.server.admin.vo.notify.NotifyTemplateAddVO;
import io.openjob.server.admin.vo.notify.NotifyTemplateQueryVO;
import io.openjob.server.admin.vo.notify.NotifyTemplateUpdateVO;
import io.openjob.server.common.dto.PageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author inhere
 * @date 2022-11-14 20:20:45
 * @since 1.0.0
 */
@Api(value = "NotifyTemplate", tags = "NotifyTemplate")
@RestController
@RequestMapping("/admin/notify-templates")
public class TemplateController {

    private final NotifyTemplateService notifyTemplateService;

    @Autowired
    public TemplateController(NotifyTemplateService notifyTemplateService) {
        this.notifyTemplateService = notifyTemplateService;
    }

    @ApiOperation("Add a notifyTemplate")
    @PostMapping("/add")
    public Result<NotifyTemplateAddVO> add(@Valid @RequestBody NotifyTemplateAddRequest addRequest) {
        return Result.success(this.notifyTemplateService.add(addRequest));
    }

    @ApiOperation("Update a notifyTemplate")
    @PostMapping("/update")
    public Result<NotifyTemplateUpdateVO> update(@Valid @RequestBody NotifyTemplateUpdateRequest updateRequest) {
        return Result.success(this.notifyTemplateService.update(updateRequest));
    }

    @ApiOperation("Delete a notifyTemplate")
    @PostMapping("/delete")
    public Result<NotifyTemplateUpdateVO> delete(@Valid @RequestBody NotifyTemplateDeleteRequest deleteRequest) {
        return Result.success(this.notifyTemplateService.delete(deleteRequest));
    }

    @ApiOperation("Get a notifyTemplate")
    @PostMapping("/get")
    public Result<NotifyTemplateQueryVO> query(@Valid @RequestBody NotifyTemplateQueryRequest getRequest) {
        return Result.success(this.notifyTemplateService.query(getRequest));
    }

    @ApiOperation("List notifyTemplate by page")
    @PostMapping("/list")
    public Result<PageDTO<NotifyTemplateQueryVO>> list(@Valid @RequestBody NotifyTemplateListRequest listRequest) {
        return Result.success(this.notifyTemplateService.getPageList(listRequest));
    }
}

