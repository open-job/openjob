package io.openjob.server.admin.vo.worker;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author riki
 * @since 1.0.0
 */
@Data
@ApiModel(value = "WorkerListVO", description = "Admin Worker list VO")
public class WorkerListVO {

    @ApiModelProperty(value = "pk")
    private Long id;

    @ApiModelProperty(value = "Appid")
    private Long appId;

    @ApiModelProperty(value = "NamespaceId")
    private Long namespaceId;

    @ApiModelProperty(value = "App name")
    private String appName;

    @ApiModelProperty(value = "Worker key")
    private String workerKey;

    @ApiModelProperty(value = "Slot id.")
    private Long slotsId;

    @ApiModelProperty(value = "Address")
    private String address;

    @ApiModelProperty(value = "Protocol type.")
    private String protocolType;

    @ApiModelProperty(value = "Version")
    private String version;

    @ApiModelProperty(value = "Last heartbeat time")
    private Long lastHeartbeatTime;

    @ApiModelProperty(value = "Status")
    private Integer status;

    @ApiModelProperty(value = "Metric")
    private String metric;

    @ApiModelProperty(value = "Worker create time")
    private Long createTime;

    @ApiModelProperty(value = "Worker update time")
    private Long updateTime;
}
