package io.openjob.server.openapi.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zhenghongyang sakuraovq@gmail.com
 * @since 1.0.0
 */
@Data
public class WorkerJobInstanceStatusRequest {

    @NotNull
    @ApiModelProperty(value = "Job id.", required = true)
    private Long jobId;

    @NotNull
    @ApiModelProperty(value = "Job instance id.", required = true)
    private Long jobInstanceId;

    @NotNull
    @ApiModelProperty(value = "Current circleId. Only for second delay task.", required = true)
    private Long circleId;

    @NotNull
    @ApiModelProperty(value = "Job instance status.", required = true)
    private Integer status;

    @NotNull
    @ApiModelProperty(value = "Fail status", required = true)
    private Integer failStatus;

    @NotNull
    @ApiModelProperty(value = "Delivery id.", required = true)
    private Long deliveryId;

    @NotNull
    @ApiModelProperty(value = "Current page.", required = true)
    private Long page;

    @Valid
    @NotNull
    @ApiModelProperty(value = "Job instance task list. Aggregation many circle task, if second delay task.", required = true)
    private List<WorkerJobInstanceTaskRequest> taskRequestList;
}
