package io.openjob.server.admin.request.alert;

import io.openjob.server.admin.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.5
 */
@Data
@ApiModel
@EqualsAndHashCode(callSuper = true)
public class ListAlertRuleRequest extends PageRequest {

    @ApiModelProperty(value = "Name", required = true)
    private String name;
}
