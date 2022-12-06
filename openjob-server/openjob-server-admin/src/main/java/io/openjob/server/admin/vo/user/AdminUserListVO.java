package io.openjob.server.admin.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author inhere
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

