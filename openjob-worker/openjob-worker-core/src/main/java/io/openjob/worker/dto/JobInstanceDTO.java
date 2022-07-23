package io.openjob.worker.dto;

import lombok.Data;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class JobInstanceDTO {
    private Long jobId;
    private Long jobInstanceId;
    private String executeType;
    private List<String> workerAddresses;
}
