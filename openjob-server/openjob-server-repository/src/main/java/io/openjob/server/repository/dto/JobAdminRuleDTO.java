package io.openjob.server.repository.dto;

import java.util.List;

import lombok.Data;

/**
 * @author inhere
 * @date 2022-11-07 13:43:04
 * @since 1.0.0
 */
@Data
public class JobAdminRuleDTO {

    /**
     * PK
     */
    private Integer id;

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
     * Is Admin. 1=yes 2=no
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

