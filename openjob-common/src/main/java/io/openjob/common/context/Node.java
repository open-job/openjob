package io.openjob.common.context;

import lombok.Data;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class Node {
    private Long serverId;
    private String ip;
    private String akkaAddress;
    private Integer status;
}
