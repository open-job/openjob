package io.openjob.server.admin.vo.notify;

import io.openjob.server.admin.vo.part.TemplateExtra;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-14 20:20:40
 * @since 1.0.0
 */
@Data
@ApiModel(value = "NotifyTemplateVO", description = "NotifyTemplate add VO")
public class NotifyTemplateAddVO {

    @ApiModelProperty(value = "PK")
    private Long id;

}

