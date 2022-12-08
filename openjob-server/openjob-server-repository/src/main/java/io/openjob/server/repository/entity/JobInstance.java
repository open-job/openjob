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
@Table(name = "job_instance")
public class JobInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Namespace id.
     */
    @Column(name = "namespace_id")
    private Long namespaceId;

    @Column(name = "app_id")
    private Long appId;

    /**
     * Job id.
     */
    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "job_params")
    private String jobParams;

    @Column(name = "status")
    private Integer status;

    /**
     * Job slots id.
     */
    @Column(name = "slots_id")
    private Long slotsId;

    @Column(name = "execute_time")
    private Long executeTime;

    @Column(name = "complete_time")
    private Integer completeTime;

    @Column(name = "last_report_time")
    private Integer lastReportTime;

    @Column(name = "deleted")
    private Integer deleted;

    /**
     * Delete time
     */
    @Column(name = "delete_time")
    private Long deleteTime;

    /**
     * Create time
     */
    @Column(name = "create_time")
    private Long createTime;

    /**
     * Update time
     */
    @Column(name = "update_time")
    private Long updateTime;
}
