package io.openjob.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
public enum StatusEnum {
    SUCCESS(200, "ok"),
    ;
    private Integer status;
    private String message;
}
