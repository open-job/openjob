package io.openjob.server.admin.request.alert;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.5
 */
@Data
@ApiModel
public class DeleteAlertRuleRequest {
    @NotNull
    @ApiModelProperty(value = "Id", required = true)
    private Long id;
}
