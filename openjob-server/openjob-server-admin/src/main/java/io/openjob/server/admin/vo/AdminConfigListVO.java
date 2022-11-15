package io.openjob.server.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-15 14:15:27
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminConfigListVO", description = "AdminConfig list VO")
public class AdminConfigListVO {

    @ApiModelProperty(value = "total count")
    private Integer total;

    @ApiModelProperty(value = "user list")
    private List<AdminConfigQueryVO> list;


}

