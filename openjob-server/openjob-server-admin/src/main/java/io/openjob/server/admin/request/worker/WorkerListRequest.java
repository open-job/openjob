package io.openjob.server.admin.request.worker;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author riki
 * @date 2022-12-26
 */
@Data
@ApiModel(value = "WorkerListRequest", description = "WorkerList page list request")
public class WorkerListRequest {

    @ApiModelProperty(value = "List page.")
    private Integer page = 1;

    @ApiModelProperty(value = "List size. default 10")
    private Integer size = 10;


    @ApiModelProperty(value = "Appid")
    private Long appId;


    @ApiModelProperty(value = "NamespaceId")
    private Long namespaceId;


    @ApiModelProperty(value = "App name")
    private String appName;
}
