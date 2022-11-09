package io.openjob.server.admin.request;

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
@ApiModel(value = "JobAdminUser", description = "JobAdminUser")
public class AdminUserRequest {

    @ApiModelProperty(value = "PK")
    private Integer id;

    @ApiModelProperty(value = "User name")
    private String username;

    @ApiModelProperty(value = "Nickname")
    private String nickname;

    @ApiModelProperty(value = "Password")
    private String passwd;

    @ApiModelProperty(value = "Api auth token")
    private String token;

    @ApiModelProperty(value = "Rule IDs. JSON: [1,2]")
    private List<Long> rule_ids;

    @ApiModelProperty(value = "Delete status. 1=yes 2=no")
    private Integer deleted;

    @ApiModelProperty(value = "Delete time")
    private Integer delete_time;

    @ApiModelProperty(value = "Update time")
    private Integer update_time;

    @ApiModelProperty(value = "Create time")
    private Integer create_time;
}

