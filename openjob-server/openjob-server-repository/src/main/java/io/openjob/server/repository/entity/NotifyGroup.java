package io.openjob.server.repository.entity;

import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
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
    private Integer deleted;

    /**
     * Delete time
     */
    @Column(name = "delete_time")
    private Long deleteTime;

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
