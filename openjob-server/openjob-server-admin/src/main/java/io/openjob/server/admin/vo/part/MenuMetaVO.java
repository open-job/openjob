package io.openjob.server.admin.vo.part;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
public class MenuMetaVO {
    @ApiModelProperty(value = "I18n title name")
    private String title;

    @ApiModelProperty(value = "Link")
    private String isLink;

    @ApiModelProperty(value = "Hide")
    private Boolean isHide;

    @ApiModelProperty(value = "Keep alive")
    private Boolean isKeepAlive;

    @ApiModelProperty(value = "Affix")
    private Boolean isAffix;

    @ApiModelProperty(value = "Iframe")
    private Boolean isIframe;

    @ApiModelProperty(value = "Roles")
    private List<String> roles;

    @ApiModelProperty(value = "icon name")
    private String icon;
}
