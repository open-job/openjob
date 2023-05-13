package io.openjob.server.admin.request.part;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author inhere
 */
@Data
public class MenuMetaRequest {
    @ApiModelProperty(value = "icon name")
    private String icon;

    @ApiModelProperty(value = "I18n title name")
    private String title;
}
