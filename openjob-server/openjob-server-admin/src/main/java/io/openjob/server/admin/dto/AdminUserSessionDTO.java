package io.openjob.server.admin.dto;

import io.openjob.server.admin.vo.part.MenuItemVO;
import io.openjob.server.admin.vo.part.PermItemVO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author inhere
 */
@Data
@Builder
public class AdminUserSessionDTO {

    /**
     * PK admin_user.id
     */
    private Long id;

    /**
     * user is supper admin
     */
    private Boolean supperAdmin;

    /**
     * User name
     */
    private String username;

    /**
     * Nickname
     */
    private String nickname;

    /**
     * Api auth token
     */
    private String token;

    /**
     * Web auth key
     */
    private String sessionKey;

    /**
     * menus for current user.
     */
    private List<MenuItemVO> menus;

    /**
     * perms for current user.
     */
    private List<PermItemVO> perms;

}
