package io.openjob.server.admin.request.notify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@ApiModel(value = "NotifyContactAddRequest", description = "NotifyContact add Request")
public class NotifyContactAddRequest {

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

}

