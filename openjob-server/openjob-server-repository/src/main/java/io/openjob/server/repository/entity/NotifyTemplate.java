package io.openjob.server.repository.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Job notify template table
 *
 * @author inhere
 * @date 2022-11-07
 */
@Data
@Entity
@Table(name = "notify_template")
@TypeDef(name = "json", typeClass = JsonType.class)
public class NotifyTemplate {

    /**
     * PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Template name
     */
    @Column(name = "name")
    private String name;

    /**
     * Template contents
     */
    @Column(name = "content")
    private String content;

    /**
     * Level. 1 notice 2 warning 3 error
     */
    @Column(name = "level")
    private String level;

    /**
     * related contact ids. JSON [12, 34]
     */
    @Type(type = "json")
    @Column(name = "contact_ids", columnDefinition = "json")
    private List<Long> contactIds;

    /**
     * related group ids. JSON [12, 34]
     */
    @Type(type = "json")
    @Column(name = "group_ids", columnDefinition = "json")
    private List<Long> groupIds;

    /**
     * Webhook URL
     */
    @Column(name = "webhook")
    private String webhook;

    /**
     * Third info. JSON: Dingding, wecom, feishu
     */
    @Column(name = "third_info")
    private String thirdInfo;

    /**
     * Creator user ID
     */
    @Column(name = "user_id")
    private Integer userId;

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
