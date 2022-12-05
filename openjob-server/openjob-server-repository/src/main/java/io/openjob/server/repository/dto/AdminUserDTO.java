package io.openjob.server.repository.dto;

import lombok.Data;

import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
public class AdminUserDTO {

    /**
     * PK
     */
    private Long id;

    /**
     * User name
     */
    private String username;

    /**
     * Nickname
     */
    private String nickname;

    /**
     * Password
     */
    private String passwd;

    /**
     * Api auth token
     */
    private String token;

    /**
     * Role IDs. JSON: [1,2]
     */
    private List<Long> roleIds;

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

