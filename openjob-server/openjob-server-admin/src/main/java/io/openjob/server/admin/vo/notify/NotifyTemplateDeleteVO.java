package io.openjob.server.admin.vo.notify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@ApiModel(value = "NotifyTemplateDeleteVO", description = "NotifyTemplate delete VO")
public class NotifyTemplateDeleteVO {

    @ApiModelProperty(value = "PK")
    private Long id;
}

