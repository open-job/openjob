package io.openjob.server.repository.entity;

import lombok.Data;

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

    @Column(name = "workflow_id")
    private Long workflowId;

    /**
     * Job id.
     */
    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "params")
    private String params;

    @Column(name = "params_type")
    private String paramsType;

    @Column(name = "extend_params_type")
    private String extendParamsType;

    @Column(name = "extend_params")
    private String extendParams;

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
    private Long completeTime;

    @Column(name = "last_report_time")
    private Long lastReportTime;

    @Column(name = "processor_type")
    private String processorType;

    @Column(name = "processor_info")
    private String processorInfo;

    @Column(name = "execute_type")
    private String executeType;

    @Column(name = "fail_retry_times")
    private Integer failRetryTimes;

    @Column(name = "fail_retry_interval")
    private Integer failRetryInterval;

    @Column(name = "concurrency")
    private Integer concurrency;

    @Column(name = "time_expression_type")
    private String timeExpressionType;

    @Column(name = "time_expression")
    private String timeExpression;

    @Column(name = "worker_address")
    private String workerAddress;

    @Column(name = "execute_strategy")
    private Integer executeStrategy;

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
     * Create time hour
     */
    @Column(name = "create_time_hour")
    private Integer createTimeHour;

    /**
     * Create time date
     */
    @Column(name = "create_time_date")
    private Integer createTimeDate;

    /**
     * Update time
     */
    @Column(name = "update_time")
    private Long updateTime;
}
