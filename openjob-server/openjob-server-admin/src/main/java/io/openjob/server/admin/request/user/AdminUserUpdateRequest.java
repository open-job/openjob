package io.openjob.server.admin.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author inhere
 * @date 2022-11-07 20:29:02
 * @since 1.0.0
 */
@Data
@ApiModel(value = "UpdateAdminUserRequest", description = "Update Admin User Request")
public class AdminUserUpdateRequest {

    @Min(1)
    @NotBlank
    @ApiModelProperty(value = "PK")
    private Long id;

    @NotBlank
    @ApiModelProperty(value = "User name")
    private String username;

    @NotNull
    @ApiModelProperty(value = "Nickname")
    private String nickname;

    @NotBlank
    @ApiModelProperty(value = "Password")
    private String passwd;

    @ApiModelProperty(value = "Api auth token")
    private String token;

    @Min(1)
    @ApiModelProperty(value = "Rule IDs. JSON: [1,2]")
    private List<Long> ruleIds;

    @ApiModelProperty(value = "Delete status. 1=yes 2=no")
    private Integer deleted;
}

