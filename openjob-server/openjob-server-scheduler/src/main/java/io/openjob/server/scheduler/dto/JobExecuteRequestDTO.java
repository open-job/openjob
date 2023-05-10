package io.openjob.server.scheduler.dto;

import lombok.Data;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class JobExecuteRequestDTO {
    private Long id;

    private String params;

    private String extendParams;
}
