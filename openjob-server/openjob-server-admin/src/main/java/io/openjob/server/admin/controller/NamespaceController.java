package io.openjob.server.admin.controller;

import io.openjob.common.response.Result;
import io.openjob.server.admin.request.namespace.AddNamespaceRequest;
import io.openjob.server.admin.request.namespace.DeleteNamespaceRequest;
import io.openjob.server.admin.request.namespace.ListNamespaceRequest;
import io.openjob.server.admin.request.namespace.UpdateNamespaceRequest;
import io.openjob.server.admin.service.NamespaceService;
import io.openjob.server.admin.vo.namespace.AddNamespaceVO;
import io.openjob.server.admin.vo.namespace.DeleteNamespaceVO;
import io.openjob.server.admin.vo.namespace.ListNamespaceVO;
import io.openjob.server.admin.vo.namespace.UpdateNamespaceVO;
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
@Api(value = "Namespace", tags = "Namespace")
@RestController
@RequestMapping("/admin/namespace")
public class NamespaceController {
    private final NamespaceService namespaceService;

    @Autowired
    public NamespaceController(NamespaceService namespaceService) {
        this.namespaceService = namespaceService;
    }

    @ApiOperation("Add namespace")
    @PostMapping("/add")
    public Result<AddNamespaceVO> add(@Valid @RequestBody AddNamespaceRequest addRequest) {
        return Result.success(this.namespaceService.add(addRequest));
    }

    @ApiOperation("Update namespace")
    @PostMapping("/update")
    public Result<UpdateNamespaceVO> update(@Valid @RequestBody UpdateNamespaceRequest updateRequest) {
        return Result.success(this.namespaceService.update(updateRequest));
    }

    @ApiOperation("Update namespace")
    @PostMapping("/delete")
    public Result<DeleteNamespaceVO> delete(@Valid @RequestBody DeleteNamespaceRequest deleteRequest) {
        return Result.success(this.namespaceService.delete(deleteRequest));
    }

    @ApiOperation("List namespace")
    @GetMapping("/list")
    public Result<PageVO<ListNamespaceVO>> list(@Valid @ModelAttribute ListNamespaceRequest listRequest) {
        return Result.success(this.namespaceService.list(listRequest));
    }
}
