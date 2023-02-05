package io.openjob.server.admin.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhenghongyang <sakuraovq@gmail.com>
 * @date 2022-11-13 18:14:02
 */
@Data
public class UpdateJobStatusRequest {

    @NotNull
    @ApiModelProperty(value = "Update namespace id", required = true)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "New namespace status", required = true)
    private Integer status;
}
