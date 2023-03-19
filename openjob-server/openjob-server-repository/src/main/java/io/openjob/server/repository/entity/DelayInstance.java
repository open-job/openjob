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
@Table(name = "delay_instance")
public class DelayInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "namespace_id")
    private Long namespaceId;

    @Column(name = "app_id")
    private Long appId;

    @Column(name = "task_id")
    private String taskId;

    @Column(name = "topic")
    private String topic;

    @Column(name = "delay_id")
    private Long delayId;

    @Column(name = "delay_params")
    private String delayParams;

    @Column(name = "delay_extra")
    private String delayExtra;

    @Column(name = "status")
    private Integer status;

    @Column(name = "execute_time")
    private Long executeTime;

    @Column(name = "complete_time")
    private Long completeTime;

    @Column(name = "worker_address")
    private String workerAddress;

    @Column(name = "deleted")
    private Integer deleted;

    /**
     * Delete time
     */
    @Column(name = "delete_time")
    private Long deleteTime;

    @Column(name = "create_time")
    private Long createTime;

    @Column(name = "update_time")
    private Long updateTime;
}
