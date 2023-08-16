package io.openjob.server.admin.request.task;

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
public class ListSecondRequest extends PageRequest {
    @ApiModelProperty(value = "Job instance id", required = true)
    private Long jobInstanceId;
}
