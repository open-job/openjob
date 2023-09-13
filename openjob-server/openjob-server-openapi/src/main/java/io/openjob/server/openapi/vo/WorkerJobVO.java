package io.openjob.server.openapi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhenghongyang sakuraovq@gmail.com
 * @since 1.0.0
 */
@Data
public class WorkerJobVO {

    @ApiModelProperty(value = "Delivery id.", required = true)
    private Long deliveryId;
}
