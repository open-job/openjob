package io.openjob.server.openapi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * @author zhenghongyang sakuraovq@gmail.com
 * @since 1.0.0
 */
@Data
public class ServerWorkerStartVO {

    @ApiModelProperty(value = "App id", required = true)
    private Long appId;

    @ApiModelProperty(value = "App name", required = true)
    private String appName;

    @ApiModelProperty(value = "Online workers  and exclude start worker.", required = true)
    private Set<String> workerAddressList;
}
