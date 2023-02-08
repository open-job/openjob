package io.openjob.server.admin.request.perm;

import io.openjob.server.admin.request.part.MenuMeta;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminPermAddRequest", description = "Add AdminPerm")
public class AdminPermAddRequest {

    @NotNull
    @Min(0)
    @ApiModelProperty(value = "Parent ID")
    private Integer pid;

    @NotNull
    @ApiModelProperty(value = "Type. 1=menu 2=perm")
    private Integer type;

    @NotNull
    @ApiModelProperty(value = "Menu name")
    private String name;

    @NotNull
    @ApiModelProperty(value = "Route path or API path")
    private String path;

    @ApiModelProperty(value = "Extra meta data. JSON object: {icon:xx,title:some.name}")
    private MenuMeta meta;

    @ApiModelProperty(value = "Hidden status. 1=yes 2=no")
    private Integer hidden;

    @NotNull
    @ApiModelProperty(value = "Sort value")
    private Integer sort;

}

