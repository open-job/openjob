package io.openjob.server.admin.vo.perm;

import io.openjob.server.admin.vo.part.MenuItemVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminPermissionMenus", description = "Admin permission menus")
public class AdminPermissionMenusVO {

    @ApiModelProperty(value = "menu list")
    private List<MenuItemVO> list;
}
