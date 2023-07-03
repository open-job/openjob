package io.openjob.server.repository.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import io.openjob.common.util.JsonUtil;
import io.openjob.server.repository.entity.json.MenuMeta;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
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
@Getter
@Setter
@Entity
@Table(name = "`admin_permission`")
@TypeDef(name = "json", typeClass = JsonType.class)
public class AdminPermission {

    /**
     * PK
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native", parameters = {@Parameter(name = "sequence_name", value = "admin_permission_id")})
    private Long id;

    /**
     * Parent ID
     */
    @Column(name = "`pid`")
    private Long pid;

    /**
     * Type. 1=menu 2=perm
     */
    @Column(name = "`type`")
    private Integer type;

    /**
     * Menu or perm name.(use for i18n or perm key)
     */
    @Column(name = "`name`")
    private String name;

    /**
     * Route path or API path
     */
    @Column(name = "`path`")
    private String path;

    /**
     * Extra meta data.
     * - JSON: {icon:xx,title:some.name,component:/@/views/path/to/page.vue}
     */
    @Column(name = "`meta`")
    private String meta;

    /**
     * Hidden status. 1=yes 2=no
     */
    @Column(name = "`hidden`")
    private Integer hidden;

    /**
     * Sort value
     */
    @Column(name = "`sort`")
    private Integer sort;

    /**
     * Delete status. 1=yes 2=no
     */
    @Column(name = "`deleted`")
    private Integer deleted;

    /**
     * Delete time
     */
    @Column(name = "`delete_time`")
    private Long deleteTime;

    /**
     * Update time
     */
    @Column(name = "`update_time`")
    private Long updateTime;

    /**
     * Create time
     */
    @Column(name = "`create_time`")
    private Long createTime;

    public MenuMeta getMetaByJson() {
        return JsonUtil.decode(this.meta, MenuMeta.class);
    }
}
