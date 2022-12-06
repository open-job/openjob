package io.openjob.server.repository.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import io.openjob.common.constant.CommonConstant;
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
 * Job system users
 *
 * @author inhere
 */
@Getter
@Setter
@Entity
@Table(name = "admin_user")
@TypeDef(name = "json", typeClass = JsonType.class)
public class AdminUser {

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
     * Role IDs. JSON: [1,2]
     */
    @Type(type = "json")
    @Column(name = "role_ids", columnDefinition = "json")
    private List<Long> roleIds;

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
