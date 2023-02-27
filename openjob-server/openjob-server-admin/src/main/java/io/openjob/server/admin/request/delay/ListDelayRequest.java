package io.openjob.server.admin.request.delay;

import io.openjob.server.admin.request.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ListDelayRequest extends PageRequest {
    @ApiModelProperty(value = "Namespace id", required = true)
    private Long namespaceId;

    @ApiModelProperty(value = "Application id", required = true)
    private Long appId;

    @ApiModelProperty(value = "Name", required = true)
    private String name;

    @ApiModelProperty(value = "Topic", required = true)
    private String topic;
}
