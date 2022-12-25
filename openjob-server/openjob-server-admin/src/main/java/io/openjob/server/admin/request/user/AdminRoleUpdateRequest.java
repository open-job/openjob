package io.openjob.server.admin.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminRoleUpdateRequest", description = "AdminRole update request")
public class AdminRoleUpdateRequest {

    @NotNull
    @Min(1)
    @ApiModelProperty(value = "PK")
    private Long id;

    @Min(1)
    @NotNull
    @ApiModelProperty(value = "Role name")
    private String name;

    @ApiModelProperty(value = "Description")
    private String desc;

    @ApiModelProperty(value = "Menu ids for role. JSON array")
    private List<Long> menus;

    @ApiModelProperty(value = "Permissions ids for role. JSON array")
    private List<Long> perms;

    @NotNull
    @ApiModelProperty(value = "Is Admin. 1=yes 2=no")
    private Integer admin;

    @ApiModelProperty(value = "Delete status. 1=yes 2=no")
    private Integer deleted;

}

