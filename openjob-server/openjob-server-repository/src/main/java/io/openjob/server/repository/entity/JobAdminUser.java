package io.openjob.server.repository.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Job system users
 *
 * @author inhere
 * @date 2022-11-07
 */
@Data
@Entity
@Table(name = "job_admin_user")
public class JobAdminUser {

    /**
     * PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * User name
     */
    @Column(name = "username")
    private String username;

    /**
     * Nickname
     */
    @Column(name = "nickname")
    private String nickname;

    /**
     * Password
     */
    @Column(name = "passwd")
    private String passwd;

    /**
     * Api auth token
     */
    @Column(name = "token")
    private String token;

    /**
     * Rule IDs. JSON: [1,2]
     */
    @Column(name = "rule_ids")
    private Rule_ids ruleIds;

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
