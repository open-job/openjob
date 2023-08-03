package io.openjob.server.admin.request.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@ApiModel(value = "LoginUserInfoRequest", description = "LoginUserInfoRequest")
public class LoginUserInfoRequest {

    @ApiModelProperty(value = "id")
    private Long id;
}
