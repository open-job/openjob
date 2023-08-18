package io.openjob.common.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Second delay to pull the child instance task by task id
 *
 * @author stelin swoft@qq.com
 * @since 1.0.7
 */
@Data
public class ServerInstanceTaskChildListPullRequest implements Serializable {

    /**
     * Job instance id
     */
    private final Long jobInstanceId;

    /**
     * Job dispatch version
     */
    private final Long dispatchVersion;

    /**
     * Task id
     */
    private String taskId;
}
