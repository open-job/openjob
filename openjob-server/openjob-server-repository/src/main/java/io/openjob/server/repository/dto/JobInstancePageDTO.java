package io.openjob.server.repository.dto;

import lombok.Data;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class JobInstancePageDTO {
    private Long id;

    private Long namespaceId;

    private Long appId;

    private Long jobId;

    private Integer status;

    private Long beginTime;

    private Long endTime;

    private Integer page;

    private Integer size;
}
