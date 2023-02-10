package io.openjob.server.admin.request.job;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhenghongyang <sakuraovq@gmail.com>
 * @date 2022-11-13 18:14:02
 */
@Data
public class DeleteJobRequest {
    @NotNull
    @ApiModelProperty(value = "Delete job id", required = true)
    private Long id;
}
