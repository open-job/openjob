package io.openjob.server.admin.request.notify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author inhere
 * @date 2022-11-14 20:21:17
 * @since 1.0.0
 */
@Data
@ApiModel(value = "NotifyContactUpdateRequest", description = "NotifyContact update request")
public class NotifyContactUpdateRequest {

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

}

