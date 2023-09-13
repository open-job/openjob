package io.openjob.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.7
 */
@Getter
@AllArgsConstructor
public enum HttpMethodEnum {
    /**
     * GET
     */
    FORM("GET"),

    /**
     * POST
     */
    JSON("POST"),
    ;

    private final String type;
}
