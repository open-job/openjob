package io.openjob.server.admin.request.job;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class UpdateJobRequest extends AddJobRequest {

    @NotNull
    @ApiModelProperty(value = "Job primary id", required = true)
    private Long id;
}
