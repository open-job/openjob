package io.openjob.server.repository.entity;

import io.openjob.common.constant.CommonConstant;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Notify contact group
 *
 * @author inhere
 */
@Data
@Entity
@Table(name = "notify_group")
public class NotifyGroup {

    /**
     * PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Group name
     */
    @Column(name = "name")
    private String name;

    /**
     * contact ids [12, 34]
     */
    @Column(name = "contact_ids")
    private String contactIds;

    /**
     * Status. 1=OK 2=disabled
     */
    @Column(name = "status")
    private Integer status;

    /**
     * Delete status. 1=yes 2=no
     */
    @Column(name = "deleted")
    private Integer deleted = CommonConstant.NO;

    /**
     * Delete time
     */
    @Column(name = "delete_time")
    private Long deleteTime = 0L;

    /**
     * Update time
     */
    @Column(name = "update_time")
    private Long updateTime;

    /**
     * Create time
     */
    @Column(name = "create_time")
    private Long createTime;
}
