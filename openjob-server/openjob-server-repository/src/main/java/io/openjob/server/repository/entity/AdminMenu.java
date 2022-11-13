package io.openjob.server.repository.entity;

import io.openjob.server.repository.entity.json.MenuMeta;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Job admin menu and perms
 *
 * @author inhere
 * @date 2022-11-07
 */
@Data
@Entity
@Table(name = "admin_menu")
public class AdminMenu {

    /**
     * PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Parent ID
     */
    @Column(name = "pid")
    private Integer pid;

    /**
     * Type. 1=menu 2=perm
     */
    @Column(name = "type")
    private Integer type;

    /**
     * Menu name
     */
    @Column(name = "name")
    private String name;

    /**
     * Route path or API path
     */
    @Column(name = "path")
    private String path;

    /**
     * Extra meta data. JSON object: {icon:xx,title:some.name}
     */
    @Column(name = "meta")
    private MenuMeta meta;

    /**
     * Hidden status. 1=yes 2=no
     */
    @Column(name = "hidden")
    private Integer hidden;

    /**
     * Sort value
     */
    @Column(name = "sort")
    private Integer sort;

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
