package io.openjob.server.alarm.dto;

import lombok.Data;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Data
public class AlarmEventDTO {

    /**
     * Job id or delay topic
     */
    private String jobUniqueId;

    /**
     * Job instance id or delay taskId
     */
    private String instanceId;

    /**
     * Event name
     */
    private String name;

    /**
     * Event message
     */
    private String message;
}
