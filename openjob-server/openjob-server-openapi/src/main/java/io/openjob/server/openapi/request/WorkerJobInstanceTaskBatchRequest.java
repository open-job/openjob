package io.openjob.server.openapi.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.8
 */
@Data
public class WorkerJobInstanceTaskBatchRequest {

    @ApiModelProperty("Job instance task list.Aggregation many circle task, if second delay task.")
    private List<WorkerJobInstanceTaskRequest> taskRequestList;

    @Data
    public static class WorkerJobInstanceTaskRequest implements Serializable {

        @NotNull
        @ApiModelProperty("Job id.")
        private Long jobId;

        @NotNull
        @ApiModelProperty("Job instance id.")
        private Long jobInstanceId;

        @NotNull
        @ApiModelProperty("Dispatch version")
        private Long dispatchVersion;

        @NotNull
        @ApiModelProperty("Only for second delay task.")
        private Long circleId;

        @NotBlank
        @ApiModelProperty("Task unique id.")
        private String taskId;

        @NotBlank
        @ApiModelProperty("Task parent unique id.")
        private String parentTaskId;

        @NotBlank
        @ApiModelProperty("Task name.")
        private String taskName;

        @NotNull
        @ApiModelProperty("Task status.")
        private Integer status;

        @NotBlank
        @ApiModelProperty("Task result.")
        private String result;

        @NotBlank
        @ApiModelProperty("Task worker address")
        private String workerAddress;

        @NotNull
        @ApiModelProperty("Task create time.")
        private Long createTime;

        @NotNull
        @ApiModelProperty("Task update time.")
        private Long updateTime;
    }
}
