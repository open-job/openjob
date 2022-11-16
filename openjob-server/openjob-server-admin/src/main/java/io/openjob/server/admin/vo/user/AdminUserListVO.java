package io.openjob.server.admin.vo.user;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author inhere
 * @date 2022-11-07 20:29:04
 * @since 1.0.0
 */
@Data
@ApiModel(value = "ListAdminUserVO", description = "ListAdminUserVO")
public class AdminUserListVO {

    @ApiModelProperty(value = "total count")
    private Integer total;

    @ApiModelProperty(value = "user list")
    private List<AdminUserQueryVO> list;

}

