package io.openjob.server.admin.request.alert;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.5
 */
@Data
@ApiModel
public class AddAlertRuleRequest {
    @NotBlank
    @ApiModelProperty(value = "Name", required = true)
    private String name;

    @NotNull
    @ApiModelProperty(value = "Name", required = true)
    private List<Integer> namespaceAppIds;

    @NotNull
    @ApiModelProperty(value = "Events")
    private List<String> events;

    @NotNull
    @ApiModelProperty(value = "metrics")
    private List<AlertRuleMetricsRequest> metrics;

    @NotBlank
    @ApiModelProperty(value = "Method", required = true)
    private String method;

    @NotBlank
    @ApiModelProperty(value = "Url", required = true)
    private String url;

    @NotNull
    @ApiModelProperty(value = "Status", required = true)
    private Integer status;

    protected static class AlertRuleMetricsRequest {

    }
}
