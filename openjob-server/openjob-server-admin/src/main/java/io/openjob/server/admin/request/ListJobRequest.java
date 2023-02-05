package io.openjob.server.admin.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhenghongyang <sakuraovq@gmail.com>
 * @date 2022-11-13 18:14:41
 */
@Data
public class ListJobRequest {

    @ApiModelProperty(value = "List page.")
    private Integer page;

    @ApiModelProperty(value = "List size.")
    private Integer size;

    @ApiModelProperty(value = "Search description.")
    private String description;

    @ApiModelProperty(value = "Filter job status.")
    private String status;
}
