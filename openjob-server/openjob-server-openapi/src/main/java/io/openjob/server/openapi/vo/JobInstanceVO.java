package io.openjob.server.openapi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhenghongyang sakuraovq@gmail.com
 * @since 1.0.0
 */
@Data
public class JobInstanceVO {

    @ApiModelProperty(value = "Job Name", required = true)
    private String jobName;

    @ApiModelProperty(value = "Data")
    private String data;
}
