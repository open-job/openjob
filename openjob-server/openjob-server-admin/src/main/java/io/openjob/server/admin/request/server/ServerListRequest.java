package io.openjob.server.admin.request.server;

import io.openjob.server.admin.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ServerListRequest", description = "ServerList page list request")
public class ServerListRequest extends PageRequest {
    @ApiModelProperty(value = "Server address")
    private String address;
}

