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
@Table(name = "`job_instance_task`")
public class JobInstanceTask {

    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native",parameters = {@Parameter(name = "sequence_name", value = "job_instance_task_id")})
    private Long id;

    @Column(name = "`job_id`")
    private Long jobId;

    @Column(name = "`job_instance_id`")
    private Long jobInstanceId;

    @Column(name = "`dispatch_version`")
    private Long dispatchVersion;

    @Column(name = "`circle_id`")
    private Long circleId;

    @Column(name = "`task_id`")
    private String taskId;

    @Column(name = "`parent_task_id`")
    private String parentTaskId;

    @Column(name = "`task_name`")
    private String taskName;

    @Column(name = "`status`")
    private Integer status;

    @Column(name = "`result`")
    private String result;

    @Column(name = "`worker_address`")
    private String workerAddress;

    @Column(name = "`deleted`")
    private Integer deleted;

    /**
     * Delete time
     */
    @Column(name = "`delete_time`")
    private Long deleteTime;

    @Column(name = "`create_time`")
    private Long createTime;

    @Column(name = "`update_time`")
    private Long updateTime;
}
