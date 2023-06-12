package io.openjob.server.openapi.vo;

import lombok.Data;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.3
 */
@Data
public class ClusterOnlineVO {
    private List<String> servers;
}
