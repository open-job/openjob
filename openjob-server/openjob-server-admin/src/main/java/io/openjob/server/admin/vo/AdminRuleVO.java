package io.openjob.server.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 21:05:06
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminRule", description = "AdminRule")
public class AdminRuleVO {

    @ApiModelProperty(value = "PK")
    private Integer id;

    @ApiModelProperty(value = "Rule name")
    private String name;

    @ApiModelProperty(value = "Description")
    private String desc;

    @ApiModelProperty(value = "Menu ids for rule. JSON array")
    private Menus menus;

    @ApiModelProperty(value = "Menu ids for rule. JSON array")
    private Perms perms;

    @ApiModelProperty(value = "Is Admin. 1=yes 2=no")
    private Integer admin;

    @ApiModelProperty(value = "Delete status. 1=yes 2=no")
    private Integer deleted;

    @ApiModelProperty(value = "Delete time")
    private Integer deleteTime;

    @ApiModelProperty(value = "Update time")
    private Integer updateTime;

    @ApiModelProperty(value = "Create time")
    private Integer createTime;
}

