package io.openjob.server.admin.vo.job;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class TimeExpressionVO {
    @ApiModelProperty(value = "Valid status", required = true)
    private Integer valid;

    @ApiModelProperty(value = "Time list", required = true)
    private List<Long> list;
}
