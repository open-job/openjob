package io.openjob.server.admin.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author inhere
 * @date 2022-11-07 20:29:04
 * @since 1.0.0
 */
@Data
@ApiModel(value = "UpdateAdminUserVO", description = "UpdateAdminUserVO")
public class AdminUserUpdateVO {

    @ApiModelProperty(value = "PK")
    private Long id;

}

