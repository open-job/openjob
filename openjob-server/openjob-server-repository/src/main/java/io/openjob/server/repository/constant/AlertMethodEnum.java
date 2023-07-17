package io.openjob.server.repository.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.5
 */
@Getter
@AllArgsConstructor
public enum AlertMethodEnum {
    /**
     * Fei shu
     */
    FEISHU("feishu"),

    /**
     * Ding ding.
     */
    DINGDING("dingding"),

    /**
     * Wecom
     */
    WECOM("wecom"),

    /**
     * Webhook
     */
    webhook("webhook"),
    ;

    private final String type;
}
