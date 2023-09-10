package io.openjob.server.scheduler.dto;

import lombok.Data;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.7
 */
@Data
public class StopTaskRequestDTO {
    private Long jobInstanceId;

    private Long dispatchVersion;

    private Long circleId;

    private String taskId;
}
