package io.openjob.server.admin.vo.part;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
public class MenuMetaVO {
    @ApiModelProperty(value = "icon name")
    private String icon;

    @ApiModelProperty(value = "I18n title name")
    private String title;
}
