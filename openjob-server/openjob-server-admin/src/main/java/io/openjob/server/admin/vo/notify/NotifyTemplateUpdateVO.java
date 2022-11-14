package io.openjob.server.admin.vo.notify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-14 20:20:42
 * @since 1.0.0
 */
@Data
@ApiModel(value = "NotifyTemplateUpdateVO", description = "NotifyTemplate update VO")
public class NotifyTemplateUpdateVO {

    @ApiModelProperty(value = "PK")
    private Long id;

}

