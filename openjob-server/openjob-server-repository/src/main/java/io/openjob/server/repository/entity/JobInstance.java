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
     * Job id.
     */
    @Column(name = "job_id")
    private Long jobId;

    /**
     * Job slots id.
     */
    @Column(name = "slots_id")
    private Long slotsId;
}
