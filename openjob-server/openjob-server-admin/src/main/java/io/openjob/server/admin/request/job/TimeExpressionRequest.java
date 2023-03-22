package io.openjob.server.admin.request.job;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class TimeExpressionRequest {
    @NotBlank
    @ApiModelProperty(value = "Expression", required = true)
    private String timeExpression;
}
