package io.openjob.server.repository.entity;

import lombok.Data;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Job admin rules
 *
 * @author inhere
 * @date 2022-11-07
 */
@Data
@Entity
@Table(name = "admin_rule")
public class AdminRule {

    /**
     * PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Rule name
     */
    @Column(name = "name")
    private String name;

    /**
     * Description
     */
    @Column(name = "desc")
    private String desc;

    /**
     * Menu ids for rule. JSON array
     */
    @Column(name = "menus")
    private String menus;
    // private List<Long> menus;

    /**
     * Menu ids for rule. JSON array
     */
    @Column(name = "perms")
    private String perms;
    // private List<Long> perms;

    /**
     * Is Admin. 1=yes 2=no
     */
    @Column(name = "admin")
    private Integer admin;

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
