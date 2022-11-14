package io.openjob.server.admin.vo.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-13 23:23:16
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminMenuUpdateVO", description = "AdminMenuUpdateVO")
public class AdminMenuUpdateVO {

    @ApiModelProperty(value = "PK")
    private Long id;

}

