package io.openjob.server.admin.vo.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminMenuDeleteVO", description = "AdminMenuDeleteVO")
public class AdminMenuDeleteVO {

    @ApiModelProperty(value = "PK")
    private Long id;
}

