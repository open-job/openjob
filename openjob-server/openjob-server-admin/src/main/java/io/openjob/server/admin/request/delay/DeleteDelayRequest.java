package io.openjob.server.admin.request.delay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class DeleteDelayRequest {
    @NotNull
    @ApiModelProperty(value = "Primary id", required = true)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "Delay cid", required = true)
    private Long cid;
}
