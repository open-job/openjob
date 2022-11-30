package io.openjob.server.admin.vo.notify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@ApiModel(value = "NotifyContactUpdateVO", description = "NotifyContact update VO")
public class NotifyContactUpdateVO {

    @ApiModelProperty(value = "PK")
    private Long id;

}

