package io.openjob.server.admin.vo.user;

import io.openjob.server.admin.vo.part.MenuMeta;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@Builder
@ApiModel(value = "AdminUserLoginVO", description = "AdminUserLoginVO")
public class AdminUserLoginVO {

    @ApiModelProperty(value = "PK")
    private Long id;

    @ApiModelProperty(value = "User name")
    private String username;

    @ApiModelProperty(value = "Nickname")
    private String nickname;

    @ApiModelProperty(value = "user is supper admin")
    private Boolean supperAdmin;

    @ApiModelProperty(value = "Manage menus for user")
    private List<MenuItem> menus;

    @ApiModelProperty(value = "Permissions for user")
    private List<PermItem> perms;

    @Data
    @ApiModel(value = "AdminUserLoginVO_MenuItem", description = "AdminUserLoginVO MenuItem")
    public static class MenuItem {

        @ApiModelProperty(value = "Menu name")
        private String name;

        @ApiModelProperty(value = "Route path or API path")
        private String path;

        @ApiModelProperty(value = "Extra meta data. JSON object: {icon:xx,title:some.name}")
        private MenuMeta meta;

        @ApiModelProperty(value = "sub menus")
        private List<MenuItem> children;
    }

    @Data
    @ApiModel(value = "AdminUserLoginVO_PermItem", description = "AdminUserLoginVO PermItem")
    public static class PermItem {

        @ApiModelProperty(value = "Perm name")
        private String name;

        @ApiModelProperty(value = "Route path or API path")
        private String path;

        @ApiModelProperty(value = "Extra meta data. JSON object: {title:some.name}")
        private MenuMeta meta;

    }

}

