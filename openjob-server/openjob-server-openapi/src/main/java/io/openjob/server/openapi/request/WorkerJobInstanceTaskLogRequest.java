package io.openjob.server.openapi.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zhenghongyang sakuraovq@gmail.com
 * @since 1.0.0
 */
@Data
public class WorkerJobInstanceTaskLogRequest {

    @NotNull
    @ApiModelProperty(value = "Field list", required = true)
    private List<List<WorkerJobInstanceTaskLogFieldRequest>> fieldList;

    @Data
    public static class WorkerJobInstanceTaskLogFieldRequest {

        @NotBlank
        @ApiModelProperty(value = "name", required = true)
        private String name;

        @NotBlank
        @ApiModelProperty(value = "value", required = true)
        private String value;
    }
}
