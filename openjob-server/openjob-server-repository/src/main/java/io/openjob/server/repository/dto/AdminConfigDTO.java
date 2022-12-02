package io.openjob.server.repository.dto;

import lombok.Data;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
public class AdminConfigDTO {

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

