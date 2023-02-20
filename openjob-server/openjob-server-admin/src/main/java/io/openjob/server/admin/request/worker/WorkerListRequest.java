package io.openjob.server.admin.request.worker;

import io.openjob.server.admin.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author riki
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "WorkerListRequest", description = "WorkerList page list request")
public class WorkerListRequest extends PageRequest {
    @ApiModelProperty(value = "Appid")
    private Long appId;

    @ApiModelProperty(value = "NamespaceId")
    private Long namespaceId;

    @ApiModelProperty(value = "App name")
    private String appName;
}
