package io.openjob.server.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("Add namespace vo")
public class AddNamespaceVO {

    @ApiModelProperty(value = "New namespace primary id", required = true)
    private Long id;
}
