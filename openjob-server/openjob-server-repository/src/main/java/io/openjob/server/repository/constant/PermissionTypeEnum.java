package io.openjob.server.repository.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum PermissionTypeEnum {
    /**
     * menu.
     */
    MENU(1, "menu"),

    /**
     * Stop.
     */
    PERMISSION(2, "permission"),
    ;

    private final Integer type;
    private final String message;
}
