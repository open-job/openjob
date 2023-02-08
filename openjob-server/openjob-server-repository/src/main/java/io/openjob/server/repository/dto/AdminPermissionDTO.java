package io.openjob.server.repository.dto;

import io.openjob.server.repository.entity.json.MenuMeta;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AdminPermissionDTO extends BaseFieldsDTO {

    /**
     * PK
     */
    private Long id;

    /**
     * Parent ID
     */
    private Long pid;

    /**
     * Type. 1=menu 2=perm
     */
    private Integer type;

    /**
     * Menu name key(perm,i18n)
     */
    private String name;

    /**
     * Route path or API path
     */
    private String path;

    /**
     * Extra meta data. JSON object: {icon:xx,title:some.name}
     */
    private MenuMeta meta;

    /**
     * Hidden status. 1=yes 2=no
     */
    private Integer hidden;

    /**
     * Sort value
     */
    private Integer sort;

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

