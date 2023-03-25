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
@Table(name = "delay")
public class Delay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pid")
    private Long pid;

    @Column(name = "cid")
    private Long cid;

    @Column(name = "namespace_id")
    private Long namespaceId;

    @Column(name = "app_id")
    private Long appId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "processor_info")
    private String processorInfo;

    @Column(name = "fail_retry_times")
    private Integer failRetryTimes;

    @Column(name = "fail_retry_interval")
    private Integer failRetryInterval;

    @Column(name = "execute_timeout")
    private Integer executeTimeout;

    @Column(name = "concurrency")
    private Integer concurrency;

    @Column(name = "blocking_size")
    private Integer blockingSize;

    @Column(name = "topic")
    private String topic;

    @Column(name = "fail_topic_enable")
    private Integer failTopicEnable;

    @Column(name = "fail_topic_concurrency")
    private Integer failTopicConcurrency;

    /**
     * Delete status. 1=yes 2=no
     */
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
