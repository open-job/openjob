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
 * Admin user session caches
 *
 * @author inhere
 * @date 2023-02-08
 */
@Getter
@Setter
@Entity
@Table(name = "admin_session")
public class AdminSession {

    /**
     * PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * User Id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * User name
     */
    @Column(name = "username")
    private String username;

    /**
     * Session token
     */
    @Column(name = "token")
    private String token;

    /**
     * Delete status. 1=yes 2=no
     */
    @Column(name = "deleted")
    private Long deleted;

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
