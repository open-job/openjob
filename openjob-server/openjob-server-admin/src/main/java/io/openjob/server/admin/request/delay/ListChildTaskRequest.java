package io.openjob.server.admin.request.delay;

import io.openjob.server.admin.request.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.7
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ListChildTaskRequest extends PageRequest {
    @ApiModelProperty(value = "Task id", required = true)
    private String taskId;
}
