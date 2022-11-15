package io.openjob.server.repository.entity;

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
 * @date 2022-11-07
 */
@Data
@Entity
@Table(name = "contact_group")
public class ContactGroup {

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
     * [12, 34]
     */
    @Column(name = "notify_user_ids")
    private String notifyUserIds;

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
    private Integer deleteTime;

    /**
     * Update time
     */
    @Column(name = "update_time")
    private Integer updateTime;

    /**
     * Create time
     */
    @Column(name = "create_time")
    private Integer createTime;
}
