package io.openjob.server.openapi.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhenghongyang <sakuraovq@gmail.com>
 * @since 1.0.0
 */
@Data
public class WorkerJobInstanceTaskLogFieldRequest {

    @NotBlank
    @ApiModelProperty(value = "name", required = true)
    private String name;

    @NotBlank
    @ApiModelProperty(value = "value", required = true)
    private String value;
}
