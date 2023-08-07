package io.openjob.server.openapi.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class WorkerFailDTO  implements Serializable {

    /**
     * Cluster version.
     */
    private Long clusterVersion;
}
