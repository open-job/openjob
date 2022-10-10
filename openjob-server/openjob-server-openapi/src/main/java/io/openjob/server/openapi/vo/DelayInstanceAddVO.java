package io.openjob.server.openapi.vo;

import lombok.Data;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class DelayInstanceAddVO {

    /**
     * Delay task unique id.
     * If is null or blank, will to auto generate.
     */
    private String taskId;
}
