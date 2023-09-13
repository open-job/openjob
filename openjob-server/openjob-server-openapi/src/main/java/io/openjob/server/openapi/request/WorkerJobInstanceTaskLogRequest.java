package io.openjob.server.openapi.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author zhenghongyang <sakuraovq@gmail.com>
 * @since 1.0.0
 */
@Data
public class WorkerJobInstanceTaskLogRequest {

    @NotNull
    @ApiModelProperty(value = "field list", required = true)
    private List<List<WorkerJobInstanceTaskLogFieldRequest>> fieldList;
}
