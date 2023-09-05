package io.openjob.server.scheduler.dto;

import lombok.Data;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.7
 */
@Data
public class TaskListPullRequestDTO {

    /**
     * Instance id.
     */
    private Long jobInstanceId;

    /**
     * Circle id
     */
    private Long circleId;
}
