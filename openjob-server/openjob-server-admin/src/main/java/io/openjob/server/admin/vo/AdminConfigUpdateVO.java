package io.openjob.server.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-15 14:15:26
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminConfigUpdateVO", description = "AdminConfig update VO")
public class AdminConfigUpdateVO {

    @ApiModelProperty(value = "PK")
    private Long id;

}

