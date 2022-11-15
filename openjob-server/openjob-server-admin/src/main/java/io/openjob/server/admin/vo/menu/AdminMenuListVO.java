package io.openjob.server.admin.vo.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-13 23:43:37
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminMenuListVO", description = "AdminMenu list")
public class AdminMenuListVO {

    @ApiModelProperty(value = "total count")
    private Integer total;

    @ApiModelProperty(value = "user list")
    private List<AdminMenuQueryVO> list;


}

