package io.openjob.server.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminConfigQueryVO", description = "AdminConfig query VO")
public class AdminConfigQueryVO {

    @ApiModelProperty(value = "PK")
    private Long id;

    @ApiModelProperty(value = "Config name")
    private String name;

    @ApiModelProperty(value = "Config value")
    private String value;

    @ApiModelProperty(value = "Delete status. 1=yes 2=no")
    private Integer deleted;

    @ApiModelProperty(value = "Delete time")
    private Long deleteTime;

    @ApiModelProperty(value = "Update time")
    private Long updateTime;

    @ApiModelProperty(value = "Create time")
    private Long createTime;
}

