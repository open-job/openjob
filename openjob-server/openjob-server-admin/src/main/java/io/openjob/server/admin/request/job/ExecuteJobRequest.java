package io.openjob.server.admin.request.job;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@ApiModel
@Data
public class ExecuteJobRequest {
    @NotNull
    @ApiModelProperty(value = "Delete job id", required = true)
    private Long id;

    @ApiModelProperty(value = "Job params")
    private String params;

    @ApiModelProperty(value = "Job extend params")
    private String extendParams;
}
