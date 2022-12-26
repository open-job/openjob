package io.openjob.server.admin.vo.server;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author riki
 * @date 2022-12-26
 */
@Data
@ApiModel(value = "ServerListVO", description = "server list VO")
public class ServerListVO {
    @ApiModelProperty(value = "pk")
    private Long id;

    /**
     * Server IP
     */
    @ApiModelProperty(value = "Server IP")
    private String ip;

    /**
     * Server address
     */
    @ApiModelProperty(value = "Server address")
    private String akkaAddress;

    /**
     * Server status
     */
    @ApiModelProperty(value = "Server status")
    private Integer status;


}
