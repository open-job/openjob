package io.openjob.server.admin.vo.notify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author inhere
 * @date 2022-11-14 20:21:20
 * @since 1.0.0
 */
@Data
@ApiModel(value = "NotifyContactQueryVO", description = "NotifyContact query VO")
public class NotifyContactQueryVO {

    @ApiModelProperty(value = "PK")
    private Long id;

    @ApiModelProperty(value = "User name")
    private String name;

    @ApiModelProperty(value = "Phone")
    private String phone;

    @ApiModelProperty(value = "Email address")
    private String email;

    @ApiModelProperty(value = "Status. 1=OK 2=disabled")
    private Integer status;

    @ApiModelProperty(value = "Delete status. 1=yes 2=no")
    private Integer deleted;

    @ApiModelProperty(value = "Delete time")
    private Long deleteTime;

    @ApiModelProperty(value = "Update time")
    private Long updateTime;

    @ApiModelProperty(value = "Create time")
    private Long createTime;
}

