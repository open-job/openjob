package io.openjob.server.repository.entity;

import lombok.Data;

import javax.persistence.Basic;
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
@Table(name = "job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "namespace_id")
    private Long namespaceId;

    @Column(name = "app_id")
    private Long appId;

    @Column(name = "slots_id")
    private int slotsId;

    @Column(name = "status")
    private byte status;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @Column(name = "processor_type")
    private byte processorType;

    @Column(name = "processor_info")
    private String processorInfo;

    @Column(name = "execute_type")
    private byte executeType;

    @Column(name = "params")
    private String params;

    @Column(name = "fail_retry_times")
    private int failRetryTimes;

    @Column(name = "fail_retry_interval")
    private int failRetryInterval;

    @Column(name = "concurrency")
    private int concurrency;

    @Column(name = "time_expression_type")
    private byte timeExpressionType;

    @Column(name = "time_expression")
    private String timeExpression;

    @Column(name = "next_execute_time")
    private int nextExecuteTime;

    @Column(name = "create_time")
    private int createTime;

    @Basic
    @Column(name = "update_time")
    private int updateTime;
}
