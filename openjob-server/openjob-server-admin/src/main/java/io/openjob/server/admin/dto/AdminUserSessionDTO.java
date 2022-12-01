package io.openjob.server.admin.dto;

import io.openjob.server.repository.dto.json.MenuMetaDTO;
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
     * perms for current user.
     */
    private List<PermItem> perms;

    @Data
    public static class PermItem {

        private String name;

        private String path;

        private MenuMetaDTO meta;

    }
}
