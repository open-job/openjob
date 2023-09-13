package io.openjob.server.openapi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * @author zhenghongyang <sakuraovq@gmail.com>
 * @since 1.0.0
 */
@Data
public class ServerHeartbeatVO {

    @ApiModelProperty("Worker address list.")
    private Set<String> workerAddressList;

    @ApiModelProperty("Server system info")
    private HeartbeatSystemVO systemResponse;

    @Data
    public static class HeartbeatSystemVO {

        @ApiModelProperty("Cluster version")
        private Long clusterVersion;

        @ApiModelProperty("Cluster delay version")
        private Long clusterDelayVersion;
    }
}
