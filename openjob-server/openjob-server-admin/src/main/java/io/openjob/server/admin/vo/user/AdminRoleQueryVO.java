package io.openjob.server.admin.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminRoleQueryVO", description = "AdminRole query VO")
public class AdminRoleQueryVO {

    @ApiModelProperty(value = "PK")
    private Long id;

    @ApiModelProperty(value = "Role name")
    private String name;

    @ApiModelProperty(value = "Description")
    private String desc;

    @ApiModelProperty(value = "Menu ids for role. JSON array")
    private List<Long> menus;

    @ApiModelProperty(value = "Permissions ids for role. JSON array")
    private List<Long> perms;

    @ApiModelProperty(value = "Is Admin. 1=yes 2=no")
    private Integer admin;

    @ApiModelProperty(value = "Delete status. 1=yes 2=no")
    private Integer deleted;

    @ApiModelProperty(value = "Delete time")
    private Long deleteTime;

    @ApiModelProperty(value = "Update time")
    private Long updateTime;

    @ApiModelProperty(value = "Create time")
    private Long createTime;
}

