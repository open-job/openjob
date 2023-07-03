package io.openjob.server.repository.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vladmihalcea.hibernate.type.json.JsonType;
import io.openjob.common.util.JsonUtil;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
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
@Table(name = "`admin_role`")
@TypeDef(name = "json", typeClass = JsonType.class)
public class AdminRole {

    /**
     * PK
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native", parameters = {@Parameter(name = "sequence_name", value = "admin_role_id")})
    private Long id;

    /**
     * Role name
     */
    @Column(name = "`name`")
    private String name;

    /**
     * Description
     */
    @Column(name = "`desc`")
    private String desc;

    /**
     * Menu ids for role. JSON array
     */
    @Column(name = "`menu_ids`")
    private String menuIds;

    /**
     * Menu ids for role. JSON array
     */
    @Column(name = "`perm_ids`")
    private String permIds;

    /**
     * Namespace ids for role. JSON array
     */
    @Column(name = "`namespace_ids`")
    private String namespaceIds;

    /**
     * App ids for role. JSON array
     */
    @Column(name = "`app_ids`")
    private String appIds;

    /**
     * Is Admin. 1=yes 2=no
     */
    @Column(name = "`admin`")
    private Integer admin;

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

    public List<Long> getMenuIdsByJson() {
        return JsonUtil.decode(this.menuIds, new TypeReference<List<Long>>() {
        });
    }


    public List<Long> getPermIdsByJson() {
        return JsonUtil.decode(this.permIds, new TypeReference<List<Long>>() {
        });
    }

    public List<Long> getNamespaceIds() {
        return JsonUtil.decode(this.namespaceIds, new TypeReference<List<Long>>() {
        });
    }

    public List<Long> getAppIds() {
        return JsonUtil.decode(this.appIds, new TypeReference<List<Long>>() {
        });
    }
}
