package io.openjob.server.repository.dto;

import lombok.Data;
import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 21:35:12
 * @since 1.0.0
 */
@Data
public class AdminRuleDTO {

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
    private Menus menus;

    /**
     * Menu ids for rule. JSON array
     */
    private Perms perms;

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

