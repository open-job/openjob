package io.openjob.server.repository.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "app")
public class App {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * NamespaceId
     */
    @Column(name = "namespace_id")
    private Long namespaceId;

    /**
     * App name
     */
    @Column(name = "name")
    private String name;

    /**
     * App desc
     */
    @Column(name = "desc")
    private String desc;

    /**
     * Create time
     */
    @Column(name = "create_time")
    private Integer createTime;

    /**
     * Update time
     */
    @Column(name = "update_time")
    private Integer updateTime;
}
