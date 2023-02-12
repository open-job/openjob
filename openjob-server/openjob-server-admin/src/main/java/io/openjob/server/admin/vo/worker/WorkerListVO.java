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

    /**
     * Appid
     */
    @ApiModelProperty(value = "Appid")
    private Long appId;

    /**
     * NamespaceId
     */
    @ApiModelProperty(value = "NamespaceId")
    private Long namespaceId;

    /**
     * App name
     */
    @ApiModelProperty(value = "App name")
    private String appName;

    /**
     * Worker key
     */
    @ApiModelProperty(value = "Worker key")
    private String workerKey;

    /**
     * Slot id.
     */
    @ApiModelProperty(value = "Slot id.")
    private Long slotsId;

    /**
     * Address
     */
    @ApiModelProperty(value = "Address")
    private String address;

    /**
     * Protocol type.
     */
    @ApiModelProperty(value = "Protocol type.")
    private String protocolType;

    /**
     * Version
     */
    @ApiModelProperty(value = "Version")
    private String version;

    /**
     * Last heartbeat time
     */
    @ApiModelProperty(value = "Last heartbeat time")
    private Long lastHeartbeatTime;

    /**
     * Status
     */
    @ApiModelProperty(value = "Status")
    private Integer status;

    /**
     * Metric
     */
    @ApiModelProperty(value = "Metric")
    private String metric;
}
