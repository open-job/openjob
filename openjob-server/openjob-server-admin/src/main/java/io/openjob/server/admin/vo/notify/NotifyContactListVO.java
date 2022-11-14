package io.openjob.server.admin.vo.notify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-14 20:21:22
 * @since 1.0.0
 */
@Data
@ApiModel(value = "NotifyContactListVO", description = "NotifyContact list VO")
public class NotifyContactListVO {

    @ApiModelProperty(value = "total count")
    private Integer total;

    @ApiModelProperty(value = "user list")
    private List<NotifyContactQueryVO> list;


}

