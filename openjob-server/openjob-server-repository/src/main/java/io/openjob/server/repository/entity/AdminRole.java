package io.openjob.server.repository.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * Job admin roles
 *
 * @author inhere
 */
@Getter
@Setter
@Entity
@Table(name = "admin_role")
@TypeDef(name = "json", typeClass = JsonType.class)
public class AdminRole {

    /**
     * PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Role name
     */
    @Column(name = "name")
    private String name;

    /**
     * Description
     */
    @Column(name = "desc")
    private String desc;

    /**
     * Menu ids for role. JSON array
     */
    @Type(type = "json")
    @Column(name = "menu_ids", columnDefinition = "json")
    private List<Long> menuIds;

    /**
     * Menu ids for role. JSON array
     */
    @Type(type = "json")
    @Column(name = "perm_ids", columnDefinition = "json")
    private List<Long> permIds;

    /**
     * Namespace ids for role. JSON array
     */
    @Type(type = "json")
    @Column(name = "namespace_ids", columnDefinition = "json")
    private List<Long> namespaceIds;

    /**
     * App ids for role. JSON array
     */
    @Type(type = "json")
    @Column(name = "app_ids", columnDefinition = "json")
    private List<Long> appIds;

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
