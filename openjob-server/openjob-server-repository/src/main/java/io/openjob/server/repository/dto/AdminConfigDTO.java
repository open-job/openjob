package io.openjob.server.repository.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author inhere
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminConfigDTO extends BaseFieldsDTO {

    /**
     * PK
     */
    private Long id;

    /**
     * Config name
     */
    private String name;

    /**
     * Config value
     */
    private String value;

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

