package io.openjob.worker.dto;

import lombok.Data;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.3
 */
@Data
public class ClusterDTO {
    private List<String> servers;
}
