package io.openjob.server.admin.vo.notify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@ApiModel(value = "NotifyTemplateListVO", description = "NotifyTemplate list VO")
public class NotifyTemplateListVO {

    @ApiModelProperty(value = "total count")
    private Integer total;

    @ApiModelProperty(value = "user list")
    private List<NotifyTemplateQueryVO> list;


}

