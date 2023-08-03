package io.openjob.server.admin.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@ApiModel(value = "LoginUserInfoVO", description = "LoginUserInfoVO")
public class LoginUserInfoVO {

    @ApiModelProperty(value = "PK")
    private Long id;

    @ApiModelProperty(value = "Username", required = true)
    private String username;

    @ApiModelProperty(value = "Nickname", required = true)
    private String nickname;

    @ApiModelProperty(value = "Login ip", required = true)
    private String loginIp;

    @ApiModelProperty(value = "Login time", required = true)
    private String loginTime;

    @ApiModelProperty(value = "Token", required = true)
    private String token;

    @ApiModelProperty(value = "Create time", required = true)
    private Long createTime;
}

