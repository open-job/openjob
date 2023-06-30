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
@Table(name = "`job_slots`")
public class JobSlots {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native",parameters = {@Parameter(name = "sequence_name", value = "job_slots_id")})
    private Long id;

    /**
     * Server id
     */
    @Column(name = "`server_id`")
    private Long serverId;

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

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
