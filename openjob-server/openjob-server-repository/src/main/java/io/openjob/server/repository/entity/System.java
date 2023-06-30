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
@Table(name = "`system`")
public class System {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native",parameters = {@Parameter(name = "sequence_name", value = "system_id")})
    private Integer id;

    /**
     * System version
     */
    @Column(name = "`version`")
    private String version;

    /**
     * Cluster version
     */
    @Column(name = "`cluster_version`")
    private Long clusterVersion;

    /**
     * Cluster delay version
     */
    @Column(name = "`cluster_delay_version`")
    private Long clusterDelayVersion;

    /**
     * Worker supervisor slot.
     */
    @Column(name = "`worker_supervisor_slot`")
    private Integer workerSupervisorSlot;

    /**
     * Delay zset slot.
     */
    @Column(name = "`delay_zset_slot`")
    private Integer delayZsetSlot;

    /**
     * Delay fail zset slot
     */
    @Column(name = "`delay_fail_zset_slot`")
    private Integer delayFailZsetSlot;

    /**
     * Delay list slot.
     */
    @Column(name = "`delay_add_list_slot`")
    private Integer delayAddListSlot;

    /**
     * Delay list slot.
     */
    @Column(name = "`delay_status_list_slot`")
    private Integer delayStatusListSlot;

    /**
     * Delay list slot.
     */
    @Column(name = "`delay_delete_list_slot`")
    private Integer delayDeleteListSlot;

    @Column(name = "`job_keep_days`")
    private Integer jobKeepDays;

    @Column(name = "`delay_keep_days`")
    private Integer delayKeepDays;

    @Column(name = "`server_keep_days`")
    private Integer serverKeepDays;

    @Column(name = "`worker_keep_days`")
    private Integer workerKeepDays;

    /**
     * Max slot
     */
    @Column(name = "`max_slot`")
    private Integer maxSlot;

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
