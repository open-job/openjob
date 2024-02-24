package io.openjob.server.openapi.request;

import io.openjob.common.constant.FailStatusEnum;
import io.openjob.common.constant.InstanceStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhenghongyang sakuraovq@gmail.com
 * @since 1.0.0
 */
@Data
public class WorkerJobInstanceStatusRequest {

    @NotNull
    @ApiModelProperty("Job id.")
    private Long jobId;

    @NotNull
    @ApiModelProperty("Job instance id.")
    private Long jobInstanceId;

    @ApiModelProperty("Current circleId. Only for second delay task.")
    private Long circleId = 0L;

    /**
     * @see InstanceStatusEnum
     */
    @NotNull
    @ApiModelProperty("Job instance status.")
    private Integer status;

    /**
     * @see FailStatusEnum#getStatus()
     */
    @NotNull
    @ApiModelProperty("Fail status")
    private Integer failStatus;

    @NotBlank
    @ApiModelProperty("Result")
    private String result;
}
