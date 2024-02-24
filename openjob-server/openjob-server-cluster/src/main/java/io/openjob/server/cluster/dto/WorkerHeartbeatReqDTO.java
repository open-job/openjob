package io.openjob.server.cluster.dto;

import lombok.Data;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.8
 */
@Data
public class WorkerHeartbeatReqDTO {
    private Long appId;

    private String appName;

    private String address;

    private String version;

    /**
     * Running job instance ids.
     */
    private List<Long> runningJobInstanceIds;
}
