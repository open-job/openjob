package io.openjob.server.admin.vo.part;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MenuMeta {
    @ApiModelProperty(value = "icon name")
    private String icon;

    @ApiModelProperty(value = "I18n title name")
    private String title;
}
