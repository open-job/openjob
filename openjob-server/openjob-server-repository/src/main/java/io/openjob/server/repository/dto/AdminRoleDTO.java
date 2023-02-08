package io.openjob.server.repository.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminRoleDTO extends BaseFieldsDTO {

    /**
     * PK
     */
    private Long id;

    /**
     * Role name
     */
    private String name;

    /**
     * Description
     */
    private String desc;

    /**
     * Menu ids for role. JSON array
     */
    private List<Long> menuIds;

    /**
     * Menu ids for role. JSON array
     */
    private List<Long> permIds;

    /**
     * Namespace ids for role. JSON array
     */
    private List<Long> namespaceIds;

    /**
     * App ids for role. JSON array
     */
    private List<Long> appIds;

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
    private Long deleteTime;

    /**
     * Update time
     */
    private Long updateTime;

    /**
     * Create time
     */
    private Long createTime;
}

