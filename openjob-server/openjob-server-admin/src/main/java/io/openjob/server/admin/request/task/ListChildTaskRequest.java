package io.openjob.server.admin.request.task;

import io.openjob.server.admin.request.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.7
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ListChildTaskRequest extends PageRequest {
    @NotBlank
    @ApiModelProperty(value = "Task id", required = true)
    private String taskId;

    @ApiModelProperty(value = "Pull", required = true)
    private String pull;
}
