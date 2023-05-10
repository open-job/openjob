package io.openjob.server.admin.vo.job;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
@ApiModel()
public class GetJobInstanceLogVO {
    @ApiModelProperty(value = "Job id", required = true)
    private Long jobId;

    @ApiModelProperty(value = "Job instance id", required = true)
    private Long jobInstanceId;

    @ApiModelProperty(value = "Job message", required = true)
    private String message;
}
