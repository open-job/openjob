package io.openjob.server.admin.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
public class AddAppVO {

    @ApiModelProperty(value = "New app primary id", required = true)
    private Long id;
}
