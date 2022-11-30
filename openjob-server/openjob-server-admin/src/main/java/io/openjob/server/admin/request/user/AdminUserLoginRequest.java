package io.openjob.server.admin.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminUserLoginRequest", description = "AdminUserLoginRequest")
public class AdminUserLoginRequest {

    @NotBlank
    @ApiModelProperty(value = "User name")
    private String username;

    @NotBlank
    @ApiModelProperty(value = "Password")
    private String passwd;

}

