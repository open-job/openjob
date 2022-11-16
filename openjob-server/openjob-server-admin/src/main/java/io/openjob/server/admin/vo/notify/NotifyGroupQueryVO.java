package io.openjob.server.admin.vo.notify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-15 14:19:59
 * @since 1.0.0
 */
@Data
@ApiModel(value = "NotifyGroupQueryVO", description = "NotifyGroup query VO")
public class NotifyGroupQueryVO {

    @ApiModelProperty(value = "PK")
    private Long id;

    @ApiModelProperty(value = "Group name")
    private String name;

    @ApiModelProperty(value = "[12, 34]")
    private List<Long> contactIds;

    @ApiModelProperty(value = "Status. 1=OK 2=disabled")
    private Integer status;

    @ApiModelProperty(value = "Delete status. 1=yes 2=no")
    private Integer deleted;

    @ApiModelProperty(value = "Delete time")
    private Long deleteTime;

    @ApiModelProperty(value = "Update time")
    private Long updateTime;

    @ApiModelProperty(value = "Create time")
    private Long createTime;
}

