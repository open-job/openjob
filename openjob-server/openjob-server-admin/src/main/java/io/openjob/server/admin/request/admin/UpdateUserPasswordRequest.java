package io.openjob.server.admin.request.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Data
public class UpdateUserPasswordRequest {
    @NotNull
    @ApiModelProperty(value = "nickname")
    private Long id;

    @NotNull
    @ApiModelProperty(value = "nickname")
    private String nickname;

    @NotNull
    @ApiModelProperty(value = "password")
    private String password;

    @NotNull
    @ApiModelProperty(value = "token")
    private String token;
}
