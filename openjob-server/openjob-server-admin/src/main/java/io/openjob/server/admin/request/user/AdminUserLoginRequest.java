package io.openjob.server.admin.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 20:29:02
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

