package io.openjob.server.admin.vo.menu;

import io.openjob.server.admin.vo.part.MenuMeta;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-13 23:23:06
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminMenuAddVO", description = "AdminMenuAddVO")
public class AdminMenuAddVO {

    @ApiModelProperty(value = "PK")
    private Long id;

}

