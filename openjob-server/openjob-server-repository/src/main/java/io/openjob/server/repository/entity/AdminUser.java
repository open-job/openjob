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
 * Job system users
 *
 * @author inhere
 */
@Getter
@Setter
@Entity
@Table(name = "`admin_user`")
@TypeDef(name = "json", typeClass = JsonType.class)
public class AdminUser {

    /**
     * PK
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native",parameters = {@Parameter(name = "sequence_name", value = "admin_user_id")})
    private Long id;

    /**
     * User name
     */
    @Column(name = "`username`")
    private String username;

    /**
     * Nickname
     */
    @Column(name = "`nickname`")
    private String nickname;

    /**
     * Password
     */
    @Column(name = "`passwd`")
    private String passwd;

    /**
     * Session expire at
     */
    @Column(name = "`session_expire_at`")
    private Long sessionExpireAt;

    /**
     * Session key
     */
    @Column(name = "`session_key`")
    private String sessionKey;

    /**
     * Api auth token
     */
    @Column(name = "`token`")
    private String token;

    /**
     * Role IDs. JSON: [1,2]
     */
    @Column(name = "`role_ids`")
    private String roleIds;

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

    public List<Long> getRoleIdsByJson(){
        return JsonUtil.decode(this.roleIds, new TypeReference<List<Long>>(){});
    }
}
