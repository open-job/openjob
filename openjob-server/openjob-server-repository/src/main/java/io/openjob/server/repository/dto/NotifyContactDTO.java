package io.openjob.server.repository.dto;

import lombok.Data;
import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 21:34:41
 * @since 1.0.0
 */
@Data
public class NotifyContactDTO {

    /**
     * PK
     */
    private Integer id;

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

