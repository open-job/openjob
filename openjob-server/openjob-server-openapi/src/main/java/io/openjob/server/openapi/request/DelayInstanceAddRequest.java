package io.openjob.server.openapi.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class DelayInstanceAddRequest {

    @ApiModelProperty("Delay task unique id. If is null or blank, will to auto generate.")
    private String taskId;

    @NotBlank
    @ApiModelProperty("topic")
    private String topic;

    @NotBlank
    @ApiModelProperty("Delay task params.")
    private String params;

    @ApiModelProperty("Delay task extra params.")
    private String extra;

    @NotNull
    @ApiModelProperty("Delay task execute timestamp.")
    private Integer executeTime;
}
