package io.openjob.server.admin.request.job;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhenghongyang sakuraovq@gmail.com
 * @since 1.0.0
 */
@Data
public class DeleteJobRequest {
    @NotNull
    @ApiModelProperty(value = "Delete job id", required = true)
    private Long id;
}
