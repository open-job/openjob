package io.openjob.server.repository.dto;

import lombok.Data;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
public class AdminSessionDTO {

    /**
     * PK
     */
    private Long id;

    /**
     * User Id
     */
    private Long userId;

    /**
     * User name
     */
    private String username;

    /**
     * Session token
     */
    private String token;

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

