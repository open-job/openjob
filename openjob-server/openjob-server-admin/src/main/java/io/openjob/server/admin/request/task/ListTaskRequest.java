package io.openjob.server.admin.request.task;

import io.openjob.server.admin.request.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.7
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ListTaskRequest extends PageRequest {
    @NotNull
    @ApiModelProperty(value = "Job instance id", required = true)
    private Long jobInstanceId;

    @NotNull
    @ApiModelProperty(value = "Time expression type", required = true)
    private String timeExpressionType;
}
