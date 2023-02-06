package io.openjob.server.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class PageVO<T> {
    @ApiModelProperty("Current page.")
    private Integer page;

    @ApiModelProperty("Page size.")
    private Integer size;

    @ApiModelProperty("Page total.")
    private Long total;

    @ApiModelProperty("Data list.")
    private List<T> list;
}
