package io.openjob.server.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author inhere <inhere@okzl.com>
 * @date 2022/11/13 21:23
 */
@Data
public class PageDTO<T> {
    @ApiModelProperty(value = "item list")
    private List<T> list;

    @ApiModelProperty(value = "total count")
    private Integer total;
}
