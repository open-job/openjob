package io.openjob.server.admin.vo.job;

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
@ApiModel()
public class AddJobVO {

    @ApiModelProperty(value = "New job primary id", required = true)
    private Long id;
}
