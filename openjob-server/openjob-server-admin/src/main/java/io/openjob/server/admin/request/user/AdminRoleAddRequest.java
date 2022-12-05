package io.openjob.server.admin.request.user;

import io.openjob.common.constant.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminRoleAddRequest", description = "AdminRole add Request")
public class AdminRoleAddRequest {

    @NotNull
    @NotBlank
    @ApiModelProperty(value = "Role name")
    private String name;

    @NotNull
    @NotBlank
    @ApiModelProperty(value = "Description")
    private String desc;

    @NotNull
    @ApiModelProperty(value = "Menu ids for role. JSON array")
    private List<Long> menus;

    @NotNull
    @ApiModelProperty(value = "Permissions ids for role. JSON array")
    private List<Long> perms;

    @NotNull
    @ApiModelProperty(value = "Is Admin. 1=yes 2=no")
    private Integer admin = CommonConstant.NO;

}

