package io.openjob.server.admin.request.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@ApiModel(value = "LoginUserInfoRequest", description = "LoginUserInfoRequest")
public class LoginUserInfoRequest {

    @NotNull
    @ApiModelProperty(value = "whether return menus")
    private Boolean withMenus = false;

    @NotNull
    @ApiModelProperty(value = "whether return menus")
    private Boolean withPerms = false;
}
