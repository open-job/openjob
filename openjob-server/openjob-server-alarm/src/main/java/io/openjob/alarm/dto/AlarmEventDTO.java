package io.openjob.alarm.dto;

import lombok.Data;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Data
public class AlarmEventDTO {

    /**
     * Job id or delay id
     */
    private Long jobId;

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
