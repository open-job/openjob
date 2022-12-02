package io.openjob.server.repository.dto;

import lombok.Data;

import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
public class AdminRuleDTO {

    /**
     * PK
     */
    private Long id;

    /**
     * Rule name
     */
    private String name;

    /**
     * Description
     */
    private String desc;

    /**
     * Menu ids for rule. JSON array
     */
    private List<Long> menus;

    /**
     * Menu ids for rule. JSON array
     */
    private List<Long> perms;

    /**
     * Is supper admin. 1=yes 2=no
     */
    private Integer admin;

    /**
     * Delete status. 1=yes 2=no
     */
    private Integer deleted;

    /**
     * Delete time
     */
    private Integer deleteTime;

    /**
     * Update time
     */
    private Integer updateTime;

    /**
     * Create time
     */
    private Integer createTime;
}

