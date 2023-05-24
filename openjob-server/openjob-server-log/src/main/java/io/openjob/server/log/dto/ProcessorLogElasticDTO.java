package io.openjob.server.log.dto;

import lombok.Data;

import java.util.Map;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.2
 */
@Data
public class ProcessorLogElasticDTO {
    private String taskId;
    private String workerAddress;
    private Map<String, String> fields;
    private Long time;
}
