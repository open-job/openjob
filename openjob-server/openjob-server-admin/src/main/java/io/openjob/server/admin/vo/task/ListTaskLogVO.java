package io.openjob.server.admin.vo.task;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.7
 */
@Data
public class ListTaskLogVO {
    @ApiModelProperty(value = "List")
    private List<String> list;

    @ApiModelProperty(value = "Time")
    private Long time;

    @ApiModelProperty(value = "Load complete")
    private Integer complete;
}
