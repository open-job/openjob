package io.openjob.server.admin.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@ApiModel()
public class ListAppVO {

    @ApiModelProperty(value = "App primary id", required = true)
    private Long id;

    @ApiModelProperty(value = "App namespace id", required = true)
    private Long namespaceId;

    @ApiModelProperty(value = "App namespace name", required = true)
    private String namespaceName;

    @ApiModelProperty(value = "App name", required = true)
    private String name;

    @ApiModelProperty(value = "App desc", required = true)
    private String desc;

    @ApiModelProperty(value = "App status", required = true)
    private Integer status;

    @ApiModelProperty(value = "App createTime", required = true)
    private Long createTime;
}
