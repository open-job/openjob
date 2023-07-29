package io.openjob.server.admin.vo.alert;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.5
 */
@Data
@ApiModel
public class ListAlertRuleVO {

    @ApiModelProperty(value = "ID", required = true)
    private Long id;

    @ApiModelProperty(value = "Name", required = true)
    private String name;

    @ApiModelProperty(value = "Name", required = true)
    private List<Long> namespaceAppIds;

    @ApiModelProperty(value = "Events")
    private List<String> events;

    @ApiModelProperty(value = "metrics")
    private List<AlertRuleMetricsVO> metrics;

    @ApiModelProperty(value = "locale")
    private String locale;

    @ApiModelProperty(value = "Method", required = true)
    private String method;

    @ApiModelProperty(value = "Url", required = true)
    private String url;

    @ApiModelProperty(value = "Secret")
    private String secret;

    @ApiModelProperty(value = "Status", required = true)
    private Integer status;

    @ApiModelProperty(value = "Update time", required = true)
    private Long updateTime;

    @ApiModelProperty(value = "Create time", required = true)
    private Long createTime;

    @Data
    public static class AlertRuleMetricsVO {

    }
}
