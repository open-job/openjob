package io.openjob.server.admin.request.job;

import io.openjob.server.admin.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhenghongyang <sakuraovq@gmail.com>
 * @date 2022-11-13 18:14:41
 */
@Data
@ApiModel
@EqualsAndHashCode(callSuper = true)
public class ListJobRequest extends PageRequest {
    @ApiModelProperty(value = "App primary id", required = true)
    private Long appId;

    @ApiModelProperty(value = "Job name", required = true)
    private String name;

    @ApiModelProperty(value = "Job status", required = true)
    private Integer status;
}
