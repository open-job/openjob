package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.notify.NotifyContactAddRequest;
import io.openjob.server.admin.request.notify.NotifyContactDeleteRequest;
import io.openjob.server.admin.request.notify.NotifyContactListRequest;
import io.openjob.server.admin.request.notify.NotifyContactQueryRequest;
import io.openjob.server.admin.request.notify.NotifyContactUpdateRequest;
import io.openjob.server.admin.service.NotifyContactService;
import io.openjob.server.admin.vo.notify.NotifyContactAddVO;
import io.openjob.server.admin.vo.notify.NotifyContactQueryVO;
import io.openjob.server.admin.vo.notify.NotifyContactUpdateVO;
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
 * @since 1.0.0
 */
@Api(value = "NotifyContact", tags = "NotifyContact")
@RestController
@RequestMapping("/admin/notify-contacts")
public class ContactController {

    private final NotifyContactService notifyContactService;

    @Autowired
    public ContactController(NotifyContactService notifyContactService) {
        this.notifyContactService = notifyContactService;
    }

    @ApiOperation("Add a notifyContact")
    @PostMapping("/add")
    public Result<NotifyContactAddVO> add(@Valid @RequestBody NotifyContactAddRequest addRequest) {
        return Result.success(this.notifyContactService.add(addRequest));
    }

    @ApiOperation("Update a notifyContact")
    @PostMapping("/update")
    public Result<NotifyContactUpdateVO> update(@Valid @RequestBody NotifyContactUpdateRequest updateRequest) {
        return Result.success(this.notifyContactService.update(updateRequest));
    }

    @ApiOperation("Delete a notifyContact")
    @PostMapping("/delete")
    public Result<NotifyContactUpdateVO> delete(@Valid @RequestBody NotifyContactDeleteRequest deleteRequest) {
        return Result.success(this.notifyContactService.delete(deleteRequest));
    }

    @ApiOperation("Get a notifyContact")
    @GetMapping("/get")
    public Result<NotifyContactQueryVO> query(@Valid NotifyContactQueryRequest getRequest) {
        return Result.success(this.notifyContactService.query(getRequest));
    }

    @ApiOperation("List notifyContact by page")
    @GetMapping("/list")
    public Result<PageDTO<NotifyContactQueryVO>> list(@Valid NotifyContactListRequest listRequest) {
        return Result.success(this.notifyContactService.getPageList(listRequest));
    }
}

