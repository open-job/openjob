package io.openjob.server.repository.dto;

import lombok.Data;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
public class NotifyContactDTO {

    /**
     * PK
     */
    private Long id;

    /**
     * User name
     */
    private String name;

    /**
     * Phone
     */
    private String phone;

    /**
     * Email address
     */
    private String email;

    /**
     * Status. 1=OK 2=disabled
     */
    private Integer status;

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

