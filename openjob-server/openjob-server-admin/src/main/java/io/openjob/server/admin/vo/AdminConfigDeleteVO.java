package io.openjob.server.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminConfigDeleteVO", description = "AdminConfig delete VO")
public class AdminConfigDeleteVO {

    @ApiModelProperty(value = "PK")
    private Long id;
}

