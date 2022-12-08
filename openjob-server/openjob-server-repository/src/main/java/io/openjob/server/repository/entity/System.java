package io.openjob.server.repository.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "system")
public class System {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * System version
     */
    @Column(name = "version")
    private String version;

    /**
     * Cluster version
     */
    @Column(name = "cluster_version")
    private Long clusterVersion;

    /**
     * Cluster supervisor slot.
     */
    @Column(name = "cluster_supervisor_slot")
    private Integer clusterSupervisorSlot;

    /**
     * Worker supervisor slot.
     */
    @Column(name = "worker_supervisor_slot")
    private Integer workerSupervisorSlot;

    /**
     * Delay zset slot.
     */
    @Column(name = "delay_zset_slot")
    private Integer delayZsetSlot;

    /**
     * Delay list slot.
     */
    @Column(name = "delay_list_slot")
    private Integer delayListSlot;

    /**
     * Max slot
     */
    @Column(name = "max_slot")
    private Integer maxSlot;
}
