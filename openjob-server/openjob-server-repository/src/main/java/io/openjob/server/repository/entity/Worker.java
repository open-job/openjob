package io.openjob.server.repository.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "`worker`")
public class Worker {

    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native", parameters = {@Parameter(name = "sequence_name", value = "worker_id")})
    private Long id;

    /**
     * Appid
     */
    @Column(name = "`app_id`")
    private Long appId;

    /**
     * NamespaceId
     */
    @Column(name = "`namespace_id`")
    private Long namespaceId;

    /**
     * App name
     */
    @Column(name = "`app_name`")
    private String appName;

    /**
     * Worker key
     */
    @Column(name = "`worker_key`")
    private String workerKey;

    /**
     * Slot id.
     */
    @Column(name = "`slots_id`")
    private Long slotsId;

    /**
     * Address
     */
    @Column(name = "`address`")
    private String address;

    /**
     * Protocol type.
     */
    @Column(name = "`protocol_type`")
    private String protocolType;

    /**
     * Version
     */
    @Column(name = "`version`")
    private String version;

    /**
     * Last heartbeat time
     */
    @Column(name = "`last_heartbeat_time`")
    private Long lastHeartbeatTime;

    /**
     * Status
     */
    @Column(name = "`status`")
    private Integer status;

    /**
     * Metric
     */
    @Column(name = "`metric`")
    private String metric;

    @Column(name = "`deleted`")
    private Integer deleted;

    /**
     * Delete time
     */
    @Column(name = "`delete_time`")
    private Long deleteTime;

    /**
     * Create time
     */
    @Column(name = "`create_time`")
    private Long createTime;

    /**
     * Update time
     */
    @Column(name = "`update_time`")
    private Long updateTime;
}
