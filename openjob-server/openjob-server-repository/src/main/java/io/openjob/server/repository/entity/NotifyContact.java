package io.openjob.server.repository.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Job notify contact
 *
 * @author inhere
 * @date 2022-11-07
 */
@Data
@Entity
@Table(name = "notify_contact")
public class NotifyContact {

    /**
     * PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * User name
     */
    @Column(name = "name")
    private String name;

    /**
     * Phone
     */
    @Column(name = "phone")
    private String phone;

    /**
     * Email address
     */
    @Column(name = "email")
    private String email;

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
