package io.openjob.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.7
 */
@Getter
@AllArgsConstructor
public enum ResponseModeEnum {
    /**
     * Http status
     */
    HTTP_STATUS("status"),

    /**
     * Json
     */
    HTTP_JSON("json"),

    /**
     * String
     */
    HTTP_STRING("string"),
    ;

    private final String mode;
}
