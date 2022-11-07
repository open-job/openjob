package io.openjob.server.repository.dto;

import lombok.Data;

/**
 * @author inhere
 * @date 2022-11-07 13:45:22
 * @since 1.0.0
 */
@Data
public class JobContactGroupDTO {

    /**
     * PK
     */
    private Integer id;

    /**
     * Group name
     */
    private String name;

    /**
     * [12, 34]
     */
    private String notifyUserIds;

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

