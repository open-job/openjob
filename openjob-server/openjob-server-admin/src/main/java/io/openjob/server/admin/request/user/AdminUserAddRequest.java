package io.openjob.server.admin.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminUserAddRequest", description = "AdminUserAddRequest")
public class AdminUserAddRequest {

    @NotNull
    @NotBlank
    @ApiModelProperty(value = "User name")
    private String username;

    @NotNull
    @ApiModelProperty(value = "Nickname")
    private String nickname;

    @NotNull
    @NotBlank
    @ApiModelProperty(value = "Password, md5 hashed before submit")
    private String passwd;

    @NotNull
    @ApiModelProperty(value = "Api auth token")
    private String token;

    @NotNull
    @Size(min = 1, max = 10)
    @ApiModelProperty(value = "Rule IDs. JSON: [1,2]")
    private List<Long> ruleIds;

}

