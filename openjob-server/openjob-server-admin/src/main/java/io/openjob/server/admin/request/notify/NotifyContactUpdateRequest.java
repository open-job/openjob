package io.openjob.server.admin.request.notify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@ApiModel(value = "NotifyContactUpdateRequest", description = "NotifyContact update request")
public class NotifyContactUpdateRequest {

    @NotNull
    @Min(1)
    @ApiModelProperty(value = "PK")
    private Long id;

    @NotNull
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

