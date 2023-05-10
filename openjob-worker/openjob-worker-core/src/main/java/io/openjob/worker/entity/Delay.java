package io.openjob.worker.entity;

import lombok.Data;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class Delay {
    private Long id;
    private String topic;
    private Integer pullSize;
    private Long pullTime;
    private Long createTime;
    private Long updateTime;
}
