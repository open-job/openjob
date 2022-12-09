package io.openjob.server.admin.vo.part;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */

@Data
@ApiModel(value = "MenuItemVO", description = "AdminUserLoginVO MenuItem")
public class MenuItemVO {

    /**
     * ID
     */
    private Long id;

    /**
     * PID
     */
    private Long pid;

    @ApiModelProperty(value = "Menu name")
    private String name;

    @ApiModelProperty(value = "Route path or API path")
    private String path;

    /**
     * Sort value
     */
    private Integer sort;

    @ApiModelProperty(value = "Extra meta data. JSON object: {icon:xx,title:some.name}")
    private MenuMetaVO meta;

    @ApiModelProperty(value = "sub menus")
    private List<MenuItemVO> children;
}
