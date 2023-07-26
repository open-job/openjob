package io.openjob.server.repository.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import io.openjob.common.util.JsonUtil;
import io.openjob.server.repository.constant.AlertMethodEnum;
import io.openjob.server.repository.constant.AlertRuleStatusEnum;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.5
 */
@Data
@Entity
@Table(name = "`alert_rule`")
public class AlertRule {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native", parameters = {@Parameter(name = "sequence_name", value = "app_id")})
    private Long id;

    /**
     * Rule name
     */
    @Column(name = "`name`")
    private String name;

    /**
     * Namespace application ids
     */
    @Column(name = "`namespace_app_ids`")
    private String namespaceAppIds;

    /**
     * Events
     */
    @Column(name = "`events`")
    private String events;

    /**
     * Metrics
     */
    @Column(name = "`metrics`")
    private String metrics;

    /**
     * Method
     *
     * @see AlertMethodEnum#getType()
     */
    @Column(name = "`method`")
    private String method;

    /**
     * Url
     */
    @Column(name = "`url`")
    private String url;

    /**
     * Status
     *
     * @see AlertRuleStatusEnum#getStatus()
     */
    @Column(name = "`status`")
    private Integer status;

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
     * Create time
     */
    @Column(name = "`create_time`")
    private Long createTime;

    /**
     * Update time
     */
    @Column(name = "`update_time`")
    private Long updateTime;

    public List<Long> getNamespaceAppIdsByJson() {
        return JsonUtil.decode(this.namespaceAppIds, new TypeReference<List<Long>>() {
        });
    }

    public List<String> getEventsByJson() {
        return JsonUtil.decode(this.getEvents(), new TypeReference<List<String>>() {
        });
    }
}
