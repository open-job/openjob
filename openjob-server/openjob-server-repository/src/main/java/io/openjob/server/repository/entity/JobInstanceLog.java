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
@Table(name = "job_instance_log")
public class JobInstanceLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "job_instance_id")
    private Long jobInstanceId;

    @Column(name = "message")
    private String message;

    /**
     * Create time
     */
    @Column(name = "create_time")
    private Integer createTime;

    /**
     * Update time
     */
    @Column(name = "update_time")
    private Integer updateTime;
}
