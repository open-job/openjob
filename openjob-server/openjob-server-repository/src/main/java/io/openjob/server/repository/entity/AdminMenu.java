package io.openjob.server.repository.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import io.openjob.common.constant.CommonConstant;
import io.openjob.server.repository.entity.json.MenuMeta;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

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
 */
@Data
@Entity
@Table(name = "admin_menu")
@TypeDef(name = "json", typeClass = JsonType.class)
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
    private Long pid;

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
    @Type(type = "json")
    @Column(name = "meta", columnDefinition = "json")
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
