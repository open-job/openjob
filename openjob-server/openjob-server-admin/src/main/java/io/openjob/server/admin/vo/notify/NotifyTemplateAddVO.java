package io.openjob.server.admin.vo.notify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@ApiModel(value = "NotifyTemplateVO", description = "NotifyTemplate add VO")
public class NotifyTemplateAddVO {

    @ApiModelProperty(value = "PK")
    private Long id;

}

