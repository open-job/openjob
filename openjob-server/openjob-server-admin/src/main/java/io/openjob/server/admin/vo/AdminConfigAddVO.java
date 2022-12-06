package io.openjob.server.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminConfigVO", description = "AdminConfig add VO")
public class AdminConfigAddVO {

    @ApiModelProperty(value = "PK")
    private Long id;
}

