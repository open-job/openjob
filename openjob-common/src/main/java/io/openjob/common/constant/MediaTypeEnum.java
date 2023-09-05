package io.openjob.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.7
 */
@AllArgsConstructor
@Getter
public enum MediaTypeEnum {
    /**
     * Form
     */
    FORM("application/x-www-form-urlencoded"),

    /**
     * JSON
     */
    JSON("application/json"),
    ;

    private final String type;
}
