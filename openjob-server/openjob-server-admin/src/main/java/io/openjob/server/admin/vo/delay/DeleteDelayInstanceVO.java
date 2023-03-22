package io.openjob.server.admin.vo.delay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class DeleteDelayInstanceVO {
    @NotNull
    @ApiModelProperty(value = "Delete job id", required = true)
    private Long id;
}
