package io.openjob.server.admin.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminConfigUpdateVO", description = "AdminConfig update VO")
public class AdminConfigUpdateVO {

    @ApiModelProperty(value = "PK")
    private Long id;

}

