package io.openjob.server.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-15 14:15:24
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminConfigVO", description = "AdminConfig add VO")
public class AdminConfigAddVO {

    @ApiModelProperty(value = "PK")
    private Long id;
}

