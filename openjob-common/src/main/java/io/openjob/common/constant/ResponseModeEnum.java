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

    /**
     * Whether is status
     *
     * @param mode mode
     * @return Boolean
     */
    public static Boolean isStatus(String mode) {
        return HTTP_STATUS.getMode().equals(mode);
    }

    /**
     * Whether is json
     *
     * @param mode mode
     * @return Boolean
     */
    public static Boolean isJson(String mode) {
        return HTTP_JSON.getMode().equals(mode);
    }

    /**
     * Whether is string
     *
     * @param mode mode
     * @return Boolean
     */
    public static Boolean isString(String mode) {
        return HTTP_STRING.getMode().equals(mode);
    }
}
