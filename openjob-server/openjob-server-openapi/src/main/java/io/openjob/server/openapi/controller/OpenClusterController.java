package io.openjob.server.openapi.controller;

import io.openjob.common.response.Result;
import io.openjob.server.openapi.request.ClusterOnlineRequest;
import io.openjob.server.openapi.service.ClusterService;
import io.openjob.server.openapi.vo.ClusterOnlineVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.3
 */
@RestController
@Api(value = "Cluster", tags = "Cluster")
@RequestMapping("/openapi/cluster")
public class OpenClusterController {
    private final ClusterService clusterService;

    @Autowired
    public OpenClusterController(ClusterService clusterService) {
        this.clusterService = clusterService;
    }

    @GetMapping("/online")
    public Result<ClusterOnlineVO> add(@Valid @ModelAttribute ClusterOnlineRequest clusterOnlineRequest) {
        return Result.success(this.clusterService.online(clusterOnlineRequest));
    }
}
