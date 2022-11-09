package io.openjob.server.admin.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author inhere
 * @date 2022-11-07 18:04:56
 * @since 1.0.0
 */
@Data
@ApiModel(value = "JobAdminConfig", description = "JobAdminConfig")
public class AdminConfigRequest {

    @ApiModelProperty(value = "PK")
    private Integer id;

    @ApiModelProperty(value = "Config name")
    private String name;

    @ApiModelProperty(value = "Config value")
    private String value;

    @ApiModelProperty(value = "Delete status. 1=yes 2=no")
    private Integer deleted;

    @ApiModelProperty(value = "Delete time")
    private Integer delete_time;

    @ApiModelProperty(value = "Update time")
    private Integer update_time;

    @ApiModelProperty(value = "Create time")
    private Integer create_time;
}

