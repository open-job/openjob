package io.openjob.server.admin.vo.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminMenuAddVO", description = "AdminMenuAddVO")
public class AdminMenuAddVO {

    @ApiModelProperty(value = "PK")
    private Long id;

}

