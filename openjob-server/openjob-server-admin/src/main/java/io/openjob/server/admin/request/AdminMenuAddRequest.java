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
 * @date 2022-11-13 23:27:24
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminMenuAdd", description = "Add AdminMenu")
public class AdminMenuAddRequest {

    @ApiModelProperty(value = "PK")
    private Integer id;

    @ApiModelProperty(value = "Parent ID")
    private Integer pid;

    @ApiModelProperty(value = "Type. 1=menu 2=perm")
    private Integer type;

    @ApiModelProperty(value = "Menu name")
    private String name;

    @ApiModelProperty(value = "Route path or API path")
    private String path;

    @ApiModelProperty(value = "Extra meta data. JSON object: {icon:xx,title:some.name}")
    private Meta meta;

    @ApiModelProperty(value = "Hidden status. 1=yes 2=no")
    private Integer hidden;

    @ApiModelProperty(value = "Sort value")
    private Integer sort;

    @ApiModelProperty(value = "Delete status. 1=yes 2=no")
    private Integer deleted;

    @ApiModelProperty(value = "Delete time")
    private Integer delete_time;

    @ApiModelProperty(value = "Update time")
    private Integer update_time;

    @ApiModelProperty(value = "Create time")
    private Integer create_time;
}

