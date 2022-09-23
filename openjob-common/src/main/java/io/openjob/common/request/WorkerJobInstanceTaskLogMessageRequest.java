package io.openjob.common.request;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class WorkerJobInstanceTaskLogMessageRequest implements Serializable {
    private String uniqueId;
    private Long jobInstanceId;
    private Long circleId;
    private Long taskId;
    private String time;
    private String thread;
    private String message;
    private String level;
    private String location;
    private String throwable;
    private Map<String, String> propertyMap;
    private String log;
}
